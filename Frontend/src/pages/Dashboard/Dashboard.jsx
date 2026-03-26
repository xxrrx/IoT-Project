import React, { useState, useEffect, useCallback } from 'react'
import './Dashboard.css'
import SensorOverview from '../../components/SensorOverview/SensorOverview'
import SwitchCard from '../../components/SwitchCard/SwitchCard'
import { assets } from '../../assets/assets'

const devices = [
  { id: 1, icon: assets.lightbulb, ledKey: 'led1', displayName: 'Đèn điện', type: 'light' },
  { id: 2, icon: assets.fan,       ledKey: 'led2', displayName: 'Quạt',     type: 'fan'   },
  { id: 3, icon: assets.wind,      ledKey: 'led3', displayName: 'Máy lạnh', type: 'ac'    },
]

const Dashboard = () => {
  // { led1: 'OFF', led2: 'OFF', led3: 'OFF' }
  const [deviceStates, setDeviceStates] = useState({ led1: 'OFF', led2: 'OFF', led3: 'OFF' })
  // Set của các ledKey đang chờ phản hồi từ server
  const [pendingDevices, setPendingDevices] = useState(new Set())
  const [errorMsg, setErrorMsg] = useState('')

  // Lấy trạng thái thiết bị khi trang load
  const fetchDeviceState = useCallback(async () => {
    try {
      const res = await fetch('http://localhost:8080/dashboard/device-state')
      const data = await res.json()
      // API trả về Led1/Led2/Led3 (viết hoa chữ L)
      setDeviceStates({
        led1: data.Led1 ?? 'OFF',
        led2: data.Led2 ?? 'OFF',
        led3: data.Led3 ?? 'OFF',
      })
    } catch (err) {
      console.error('Không thể lấy trạng thái thiết bị:', err)
    }
  }, [])

  useEffect(() => {
    fetchDeviceState()
  }, [fetchDeviceState])

  const handleToggle = async (ledKey, currentIsOn) => {
    const newState = currentIsOn ? 'OFF' : 'ON'

    // Chuyển sang trạng thái pending
    setPendingDevices(prev => new Set(prev).add(ledKey))
    setErrorMsg('')

    try {
      const res = await fetch(
        `http://localhost:8080/dashboard/device-control?led=${ledKey}&state=${newState}`,
        { method: 'POST' }
      )
      const data = await res.json()

      if (res.ok && data.success) {
        setDeviceStates(prev => ({ ...prev, [ledKey]: newState }))
      } else {
        setErrorMsg(data.message || 'Điều khiển thiết bị không thành công.')
      }
    } catch (err) {
      setErrorMsg('Không thể kết nối đến server.')
      console.error(err)
    } finally {
      // Xóa khỏi pending dù thành công hay thất bại
      setPendingDevices(prev => {
        const next = new Set(prev)
        next.delete(ledKey)
        return next
      })
    }
  }

  return (
    <div className="dashboard">
      {errorMsg && (
        <div className="error-modal-overlay" onClick={() => setErrorMsg('')}>
          <div className="error-modal" onClick={e => e.stopPropagation()}>
            <div className="error-modal-icon">⚠️</div>
            <h3 className="error-modal-title">Điều khiển thất bại</h3>
            <p className="error-modal-message">{errorMsg}</p>
            <button className="error-modal-close" onClick={() => setErrorMsg('')}>Đóng</button>
          </div>
        </div>
      )}
      <div className='sensor-overview'>
        <SensorOverview/>
      </div>
      <div className="switch-cards">
        {devices.map(device => (
          <SwitchCard
            key={device.id}
            icon={device.icon}
            name={device.ledKey}
            displayName={device.displayName}
            type={device.type}
            isOn={deviceStates[device.ledKey] === 'ON'}
            isPending={pendingDevices.has(device.ledKey)}
            onToggle={() => handleToggle(device.ledKey, deviceStates[device.ledKey] === 'ON')}
          />
        ))}
      </div>
    </div>
  )
}

export default Dashboard
