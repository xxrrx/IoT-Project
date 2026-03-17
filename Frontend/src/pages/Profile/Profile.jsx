import React from 'react'
import './Profile.css'
import { assets } from '../../assets/assets'

const Profile = () => {
  const profileData = {
    name: 'Nguyễn Đức Thuận',
    email: 'hellothuan12112004@gmail.com',
    studentId: 'B22DCPT268',
    faculty: 'Đa phương tiện',
    avatar: 'https://via.placeholder.com/200' // Replace with actual image
  }

  const resources = [
    {
      id: 1,
      icon: assets.github,
      title: 'GitHub Repository',
      link: '#',
      bgColor: '#F5F5F5',
      iconColor: '#000'
    },
    {
      id: 2,
      icon: assets.figma,
      title: 'Figma',
      link: '#',
      bgColor: '#FFF4ED',
      iconColor: '#FF7262'
    },
    {
      id: 3,
      icon: assets.fileLinesSolid,
      title: 'Báo cáo Final',
      link: '#',
      bgColor: '#FFEBEE',
      iconColor: '#FF5252'
    },
    {
      id: 4,
      icon: assets.fileLinesSolid,
      title: 'API Documentation',
      link: '#',
      bgColor: '#FFF4ED',
      iconColor: '#FF9800'
    }
  ]

  return (
    <div className="profile-container">
      <div className="profile-card">
        {/* Profile Header */}
        <div className="profile-header">
          <div className="avatar-wrapper">
            <img src={profileData.avatar} alt={profileData.name} className="avatar" />
          </div>
          <h1 className="profile-name">{profileData.name}</h1>
          <p className="profile-email">{profileData.email}</p>
        </div>

        {/* Personal Information */}
        <div className="info-section">
          <h2 className="section-title">Thông tin cá nhân</h2>
          <div className="info-grid">
            <div className="info-row">
              <div className="info-item">
                <span className="info-label">Họ và tên:</span>
                <span className="info-value">{profileData.name}</span>
              </div>
              <div className="info-item">
                <span className="info-label">Mã sinh viên:</span>
                <span className="info-value">{profileData.studentId}</span>
              </div>
            </div>
            <div className="info-row">
              <div className="info-item">
                <span className="info-label">Email:</span>
                <span className="info-value">{profileData.email}</span>
              </div>
              <div className="info-item">
                <span className="info-label">Khoa:</span>
                <span className="info-value">{profileData.faculty}</span>
              </div>
            </div>
          </div>
        </div>

        {/* Resources Section */}
        <div className="resources-section">
          <h2 className="section-title">Tài liệu và liên kết</h2>
          <div className="resources-grid">
            {resources.map(resource => (
              <a
                key={resource.id}
                href={resource.link}
                className="resource-card"
                style={{ backgroundColor: resource.bgColor }}
              >
                <div className="resource-icon" style={{ backgroundColor: resource.iconColor }}>
                  <img src={resource.icon} alt={resource.title} />
                </div>
                <span className="resource-title">{resource.title}</span>
              </a>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}

export default Profile
