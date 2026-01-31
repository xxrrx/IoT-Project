import React, { useState } from 'react'
import './SensorOverview.css'
import { assets } from '../../assets/assets'
import Chart from '../Chart/Chart'

const SensorOverview = () => {
  const [activeSensor, setActiveSensor] = useState('temperature')

  const background = {
    humidity: "rgba(123,216,235,0.3), #11B4D4",
    temperature: "#FFA567, #f97316",
    light: "rgba(255,205,0,0.3), #FBDE23"
  }

  return (
    <div className="sensor-overview">
      <div className="card" style={{
            background: `linear-gradient(to bottom,${background[activeSensor]})`
          }}>
          <div className="content">
              <div className="header">
                <h2>Tổng quan</h2>
                <div className='filter'>
                  <p>30 dữ liệu gần nhất</p>
                  <img src={assets.angleDown} alt="" />
                </div>
              </div>
              <Chart activeSensor={activeSensor}/>
          </div>
          <div className={`footer ${activeSensor}`}>
              <div 
                className={`item ${activeSensor === 'humidity' ? 'active' : ''}`}
                onClick={() => setActiveSensor('humidity')}
              >
                <p>Độ ẩm trung bình</p>
                <h2>100%</h2>
              </div>

              <div 
                className={`item ${activeSensor === 'temperature' ? 'active' : ''}`}
                onClick={() => setActiveSensor('temperature')}
              >
                <p>Nhiệt độ trung bình</p>
                <h2>18.3 °C</h2>
              </div>

              <div 
                className={`item ${activeSensor === 'light' ? 'active' : ''}`}
                onClick={() => setActiveSensor('light')}
              >
                <p>Ánh sáng trung bình</p>
                <h2>100 lx</h2>
              </div>
          </div>
      </div>

      <div className="card-sensor">
        <div className="sensor-item humidity">
          <h3>Độ ẩm</h3>
          <div className='card-data'>
            <div className="icon-wrapper">
              <img src={assets.droplet} alt="" />
            </div>
            <p>100 %</p>
          </div>
        </div>
        <div className="sensor-item temperature">
          <h3>Nhiệt độ</h3>
          <div className='card-data'>
            <div className="icon-wrapper">
              <img src={assets.temperatureHigh} alt="" />
            </div>
            <p>18.3 °C</p>
          </div>
        </div>
        <div className="sensor-item light">
          <h3>Ánh sáng</h3>
          <div className='card-data'>
            <div className="icon-wrapper">
              <img src={assets.lightbulb} alt="" />
            </div>
            <p>100 lx</p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SensorOverview
