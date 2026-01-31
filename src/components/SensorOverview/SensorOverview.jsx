import React from 'react'
import './SensorOverview.css'
import { assets } from '../../assets/assets'

const SensorOverview = () => {
  return (
    <div className="sensor-overview">
      <div className="card">
          <div className="content">
              <div className="header">
                <h2>Tổng quan</h2>
                <div className='filter'>
                  <p>30 dữ liệu gần nhất</p>
                  <img src={assets.angleDown} alt="" />
                </div>
              </div>
              
          </div>
          <div className="footer">
              <div className="item">
                <p>Độ ẩm trung bình</p>
                <h2>100%</h2>
              </div>

              <div className="item">
                <p>Nhiệt độ trung bình</p>
                <h2>18.3 °C</h2>
              </div>

              <div className="item">
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
