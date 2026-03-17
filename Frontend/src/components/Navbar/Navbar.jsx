import React from 'react'
import './Navbar.css'
import { useLocation } from 'react-router-dom'

const Navbar = () => {
  const location = useLocation();

  const getPageTitle = () => {
    switch(location.pathname) {
      case '/':
        return 'Dashboard';
      case '/data-sensor':
        return 'Data Sensor';
      case '/action-history':
        return 'Action History';
      case '/profile':
        return 'Profile';
      default:
        return 'Dashboard';
    }
  }

  const date = new Date().toLocaleDateString("vi-VN");

  return (
    <div className='navbar'>
      <h1>{getPageTitle()}</h1>
      <p>{date}</p>
    </div>
  )
}

export default Navbar
