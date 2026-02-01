import React from 'react'
import './Dashboard.css'
import SensorOverview from '../../components/SensorOverview/SensorOverview'
import SwitchCard from '../../components/SwitchCard/SwitchCard'
import { assets } from '../../assets/assets'

  const devices = [
    {
      id: 1,
      icon: assets.lightbulb,
      name: 'light',
      displayName: 'Đèn điện',
      defaultState: false
    },
    {
      id: 2,
      icon: assets.fan,
      name: 'fan',
      displayName: 'Quạt',
      defaultState: false
    },
    {
      id: 3,
      icon: assets.wind,
      name: 'airconditioner',
      displayName: 'Điều hòa',
      defaultState: false
    }
  ]

const Dashboard = () => {
  return (
    <div className="dashboard">
      <div className='sensor-overview'>
        <SensorOverview/>
      </div>
      <div className="switch-cards">
        {devices.map(device => (
          <SwitchCard
            key={device.id}
            icon={device.icon}
            name={device.name}
            displayName={device.displayName}
            defaultState={device.defaultState}
          />
        ))}
      </div>
    </div>
  )
}

export default Dashboard
