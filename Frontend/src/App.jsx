import React from 'react'
import Navbar from './components/Navbar/Navbar'
import Sidebar from './components/SideBar/Sidebar'
import { Route, Routes } from 'react-router-dom'
import Dashboard from './pages/Dashboard/Dashboard'
import Datasensor from './pages/DataSensor/Datasensor'
import ActionHistory from './pages/ActionHistory/ActionHistory'
import Profile from './pages/Profile/Profile'

const App = () => {
  return (
    <div className='app'>
      <Sidebar/>
      <div className='main-content'>
        <Navbar/>
        <Routes>
          <Route path='/' element={<Dashboard />} />
          <Route path='/data-sensor' element={<Datasensor />} />
          <Route path='/action-history' element={<ActionHistory />} />
          <Route path='/profile' element={<Profile />} />
        </Routes>
      </div>
    </div>
  )
}

export default App
