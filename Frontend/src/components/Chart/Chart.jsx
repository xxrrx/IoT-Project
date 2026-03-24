import React from 'react'
import './Chart.css'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, ResponsiveContainer } from 'recharts'

const Chart = ({ data = fallbackData, yDomain = [0, 30], yTicks = [0, 10, 20, 30], isAnimationActive = false }) => {

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
            interval={Math.max(0, Math.ceil(data.length / 10) - 1)}
          />
          <YAxis 
            stroke="white"
            tick={{ fill: 'white', fontSize: 16, fontWeight: 600 }}
            tickLine={false}
            axisLine={false}
            domain={yDomain}
            ticks={yTicks}
          />
          <Line
            type="monotone"
            dataKey="value"
            stroke="white"
            strokeWidth={2}
            dot={false}
            isAnimationActive={isAnimationActive}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}

export default Chart
