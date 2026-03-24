import React, { createContext, useState, useEffect, useCallback, useRef } from 'react'

export const AppContext = createContext()

const AppContextProvider = ({ children }) => {
  const [dashboardData, setDashboardData] = useState(null)
  const [sensorData, setSensorData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [quantity, setQuantity] = useState(20)
  const [animateChart, setAnimateChart] = useState(true)
  const firstFetchDone = useRef(false)
  const sseRef = useRef(null)
  const reconnectTimer = useRef(null)

  const formatTime = (recordAt) => {
    const date = new Date(recordAt)
    const h = date.getHours().toString().padStart(2, '0')
    const m = date.getMinutes().toString().padStart(2, '0')
    return `${h}:${m}`
  }

  const calcDomain = (arr) => {
    const values = arr.map(d => d.value)
    const min = Math.min(...values)
    const max = Math.max(...values)
    const padding = (max - min) * 0.3 || 1
    const domainMin = parseFloat((min - padding).toFixed(1))
    const domainMax = parseFloat((max + padding).toFixed(1))
    return [domainMin, domainMax]
  }

  const calcTicks = ([min, max]) => {
    const step = (max - min) / 4
    return Array.from({ length: 5 }, (_, i) =>
      parseFloat((min + i * step).toFixed(1))
    )
  }

  const mapToChartData = (arr) =>
    [...arr].reverse().map(item => ({
      time: formatTime(item.recordAt),
      value: item.value
    }))

  const fetchDashboard = useCallback(async () => {
    try {
      const res = await fetch(`http://localhost:8080/dashboard/chart?quantity=${quantity}`)
      const data = await res.json()

      const tempDomain = calcDomain(data.temperature)
      const humDomain  = calcDomain(data.humidity)
      const lightDomain = calcDomain(data.light)

      setDashboardData({
        chartData: {
          temperature: {
            data: mapToChartData(data.temperature),
            yDomain: tempDomain,
            yTicks: calcTicks(tempDomain)
          },
          humidity: {
            data: mapToChartData(data.humidity),
            yDomain: humDomain,
            yTicks: calcTicks(humDomain)
          },
          light: {
            data: mapToChartData(data.light),
            yDomain: lightDomain,
            yTicks: calcTicks(lightDomain)
          }
        },
        averages: {
          temperature: data.avarageTemp,
          humidity: data.avarageHumidity,
          light: data.avarageLight
        }
      })

      if (!firstFetchDone.current) {
        firstFetchDone.current = true
        setTimeout(() => setAnimateChart(false), 1000)
      }
    } catch (err) {
      console.error('Failed to fetch dashboard data:', err)
    } finally {
      setLoading(false)
    }
  }, [quantity])

  useEffect(() => {
    firstFetchDone.current = false
    setAnimateChart(true)
  }, [quantity])

  const connectSse = useCallback(() => {
    if (sseRef.current) sseRef.current.close()

    const sse = new EventSource('http://localhost:8080/dashboard/sensor/stream')
    sseRef.current = sse

    sse.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data)
        setSensorData(data)
      } catch (err) {
        console.error('SSE parse error:', err)
      }
    }

    sse.onerror = () => {
      sse.close()
      sseRef.current = null
      reconnectTimer.current = setTimeout(() => {
        connectSse()
      }, 3000)
    }
  }, [])

  useEffect(() => {
    fetchDashboard()
    const interval = setInterval(fetchDashboard, 2000)
    return () => clearInterval(interval)
  }, [fetchDashboard])

  useEffect(() => {
    connectSse()
    return () => {
      if (sseRef.current) sseRef.current.close()
      if (reconnectTimer.current) clearTimeout(reconnectTimer.current)
    }
  }, [connectSse])

  return (
    <AppContext.Provider value={{ dashboardData, sensorData, loading, quantity, setQuantity, animateChart }}>
      {children}
    </AppContext.Provider>
  )
}

export default AppContextProvider


