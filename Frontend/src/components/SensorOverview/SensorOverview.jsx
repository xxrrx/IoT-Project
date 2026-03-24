import React, { useState, useContext } from 'react'
import './SensorOverview.css'
import { assets } from '../../assets/assets'
import Chart from '../Chart/Chart'
import SensorCards from './SensorCards'
import { AppContext } from '../../Context/AppContext'

const SensorOverview = () => {
  const [activeSensor, setActiveSensor] = useState('temperature')
  const { dashboardData, loading, quantity, setQuantity, animateChart } = useContext(AppContext)

  const background = {
    humidity: "rgba(123,216,235,0.3), #11B4D4",
    temperature: "#FFA567, #f97316",
    light: "rgba(255,205,0,0.3), #FBDE23"
  }

  const currentChart = dashboardData?.chartData[activeSensor]

  const formatAvg = (sensor) => {
    if (!dashboardData) return '...'
    const val = dashboardData.averages[sensor]
    if (sensor === 'temperature') return `${val.toFixed(1)} °C`
    if (sensor === 'humidity') return `${val.toFixed(1)} %`
    if (sensor === 'light') return `${val.toFixed(0)} lx`
  }

  return (
    <div className="sensor-overview">
      <div className="card" style={{
            background: `linear-gradient(to bottom,${background[activeSensor]})`
          }}>
          <div className="content">
              <div className="header">
                <h2>Tổng quan</h2>
                <select
                  className='filter'
                  value={quantity}
                  onChange={(e) => setQuantity(Number(e.target.value))}
                >
                  {[50, 100, 150, 200, 250].map(n => (
                    <option key={n} value={n}>{n} dữ liệu gần nhất</option>
                  ))}
                </select>
              </div>
              {loading || !currentChart
                ? <p style={{ color: 'white', textAlign: 'center', paddingTop: '60px' }}>Đang tải...</p>
                : <Chart
                    data={currentChart.data}
                    yDomain={currentChart.yDomain}
                    yTicks={currentChart.yTicks}
                    isAnimationActive={animateChart}
                  />
              }
          </div>
          <div className={`footer ${activeSensor}`}>
              <div
                className={`item ${activeSensor === 'humidity' ? 'active' : ''}`}
                onClick={() => setActiveSensor('humidity')}
              >
                <p>Độ ẩm trung bình</p>
                <h2>{formatAvg('humidity')}</h2>
              </div>

              <div
                className={`item ${activeSensor === 'temperature' ? 'active' : ''}`}
                onClick={() => setActiveSensor('temperature')}
              >
                <p>Nhiệt độ trung bình</p>
                <h2>{formatAvg('temperature')}</h2>
              </div>

              <div
                className={`item ${activeSensor === 'light' ? 'active' : ''}`}
                onClick={() => setActiveSensor('light')}
              >
                <p>Ánh sáng trung bình</p>
                <h2>{formatAvg('light')}</h2>
              </div>
          </div>
      </div>

      <SensorCards />
    </div>
  )
}

export default SensorOverview
