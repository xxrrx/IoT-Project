import React from 'react'
import './Dashboard.css'
import SensorOverview from '../../components/SensorOverview/SensorOverview'
import SwitchCard from '../../components/SwitchCard/SwitchCard'

const Dashboard = () => {
  return (
    <div className="dashboard">
      <div className='sensor-overview'>
        <SensorOverview/>
      </div>
      <div className="switch-cards">
        <SwitchCard/>
        <SwitchCard/>
        <SwitchCard/>
      </div>
    </div>
  )
}

export default Dashboard
