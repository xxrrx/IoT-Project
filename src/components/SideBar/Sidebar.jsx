import React from 'react'
import './Sidebar.css'
import { assets } from '../../assets/assets'
import { NavLink } from 'react-router-dom'

const Sidebar = () => {
  return (
    <div className='sidebar'>
      <div className='sidebar-container'>
        <div className='sidebar-header'>
          <h1>THL System</h1>
          <hr />
        </div>
        <ul className='sidebar-list'>
          <NavLink to={'/'} className='sidebar-item'><img src={assets.squarePoll} alt="dashboard" />Dashboard</NavLink>
          <NavLink to={'/data-sensor'} className='sidebar-item'><img src={assets.fileLinesSolid} alt="data" />Data sensor</NavLink>
          <NavLink to={'/action-history'} className='sidebar-item'><img src={assets.clockRotateLeft} alt="history" />Action history</NavLink>
          <NavLink to={'/profile'} className='sidebar-item'><img src={assets.user} alt="profile" />Profile</NavLink>
        </ul>
        <div className='sidebar-footer'>
          <hr />
          <h2>Admin Panel</h2>
        </div>
      </div>
    </div>
  )
}

export default Sidebar
