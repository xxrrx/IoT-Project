import React, { useState } from 'react'
import './SwitchCard.css'
import { assets } from '../../assets/assets'

const SwitchCard = ({ icon = assets.lightbulb, name = 'device', displayName = 'Thiết bị', defaultState = false }) => {
  const [isOn, setIsOn] = useState(defaultState)

  const handleToggle = () => {
    setIsOn(!isOn)
    // TODO: Gửi request lên server để điều khiển thiết bị
    console.log(`${name} is now ${!isOn ? 'ON' : 'OFF'}`)
  }

  return (
    <div className="switch-card">
      <div className="card-header">
        <div className="icon-container">
          <img src={icon} alt={name} />
        </div>
      </div>
      <div className="card-body">
        <h3>{displayName}</h3>
        <div className="toggle-wrapper">
          <input 
            type="checkbox" 
            className="toggle-checkbox" 
            id={`toggle-${name}`}
            checked={isOn}
            onChange={handleToggle}
          />
          <label 
            className="toggle-label" 
            htmlFor={`toggle-${name}`}
          >
            <span className="toggle-button"></span>
          </label>
        </div>
      </div>
      <p className="status">Trạng thái: <span>{isOn ? 'Bật' : 'Tắt'}</span></p>
    </div>
  )
}

export default SwitchCard
