import React from 'react'
import './Dashboard.css'
import SensorOverview from '../../components/SensorOverview/SensorOverview'

const Dashboard = () => {
  return (
    <div className="dashboard">
      <div className='sensor-overview'>
        <SensorOverview/> 
      </div>
    </div>
  )
}

export default Dashboard
