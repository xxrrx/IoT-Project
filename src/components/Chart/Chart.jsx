import React from 'react'
import './Chart.css'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, ResponsiveContainer } from 'recharts'

const Chart = () => {
  const data = [
    { time: '7:00', value: 13 },
    { time: '8:00', value: 20 },
    { time: '9:00', value: 21 },
    { time: '10:00', value: 22 },
    { time: '11:00', value: 23 },
    { time: '12:00', value: 22 },
    { time: '13:00', value: 21 },
    { time: '14:00', value: 20 },
    { time: '15:00', value: 17 },
    { time: '16:00', value: 14 },
  ]

  return (
    <div className="chart-container">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart 
          data={data}
          margin={{ top: 25, right: 30, left: 10, bottom: 5 }}
        >
          <CartesianGrid 
            strokeDasharray="0" 
            stroke="rgba(255, 255, 255, 0.2)" 
            horizontal={true} 
            vertical={false}
          />
          <XAxis 
            dataKey="time" 
            stroke="white"
            tick={{ fill: 'white', fontSize: 14, fontWeight: 500 }}
            tickLine={false}
            axisLine={{ stroke: 'rgba(255, 255, 255, 1)' }}
          />
          <YAxis 
            stroke="white"
            tick={{ fill: 'white', fontSize: 16, fontWeight: 600 }}
            tickLine={false}
            axisLine={false}
            domain={[0, 30]}
            ticks={[0, 10, 20, 30]}
          />
          <Line 
            type="monotone" 
            dataKey="value" 
            stroke="white" 
            strokeWidth={4}
            dot={false}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}

export default Chart
