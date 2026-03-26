import React from 'react'
import './SwitchCard.css'
import { assets } from '../../assets/assets'

const SwitchCard = ({ icon = assets.lightbulb, name = 'device', displayName = 'Thiết bị', type = 'light', isOn = false, isPending = false, onToggle }) => {
  return (
    <div className={`switch-card${isPending ? ' pending' : ''}`}>
      <div className="card-header">
        <div className={`icon-container${isOn ? ` icon-on--${type}` : ''}`}>
          <img
            src={icon}
            alt={name}
            className={isOn && type === 'fan' ? 'fan-spinning' : ''}
          />
        </div>
      </div>
      <div className="card-body">
        <h3>{displayName}</h3>
        <div className="toggle-wrapper">
          {isPending && <span className="toggle-spinner"></span>}
          <input
            type="checkbox"
            className="toggle-checkbox"
            id={`toggle-${name}`}
            checked={isOn}
            onChange={onToggle}
            disabled={isPending}
          />
          <label
            className={`toggle-label${isPending ? ' toggle-label--pending' : ''}`}
            htmlFor={`toggle-${name}`}
          >
            <span className="toggle-button"></span>
          </label>
        </div>
      </div>
      <p className="status">
        Trạng thái: <span>{isPending ? 'Đang xử lý...' : isOn ? 'Bật' : 'Tắt'}</span>
      </p>
    </div>
  )
}

export default SwitchCard

