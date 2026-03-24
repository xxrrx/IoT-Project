import React, { useContext } from 'react'
import { assets } from '../../assets/assets'
import { AppContext } from '../../Context/AppContext'

const SensorCards = () => {
  const { sensorData } = useContext(AppContext)

  const renderValue = (value, unit) => {
    if (!sensorData) return '...'
    if (value === null || value === undefined) return '...'
    return `${value} ${unit}`
  }

  return (
    <div className="card-sensor">
      <div className="sensor-item humidity">
        <h3>Độ ẩm</h3>
        <div className="card-data">
          <div className="icon-wrapper">
            <img src={assets.droplet} alt="Humidity icon" />
          </div>
          <p>{renderValue(sensorData?.humidity, '%')}</p>
        </div>
      </div>

      <div className="sensor-item temperature">
        <h3>Nhiệt độ</h3>
        <div className="card-data">
          <div className="icon-wrapper">
            <img src={assets.temperatureHigh} alt="Temperature icon" />
          </div>
          <p>{renderValue(sensorData?.temperature, '°C')}</p>
        </div>
      </div>

      <div className="sensor-item light">
        <h3>Ánh sáng</h3>
        <div className="card-data">
          <div className="icon-wrapper">
            <img src={assets.lightbulb} alt="Light icon" />
          </div>
          <p>{renderValue(sensorData?.light, 'lx')}</p>
        </div>
      </div>
    </div>
  )
}

export default SensorCards
