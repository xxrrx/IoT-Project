import React from 'react'
import './Profile.css'
import { assets } from '../../assets/assets'
import ActionHistory from '../ActionHistory/ActionHistory'

const Profile = () => {
  const profileData = {
    name: 'Nguyễn Đức Thuận',
    email: 'hellothuan12112004@gmail.com',
    studentId: 'B22DCPT268',
    faculty: 'Đa phương tiện',
    avatar: assets.avatar 
  }

  const resources = [
    {
      id: 1,
      icon: assets.github,
      title: 'GitHub Repository',
      link: 'https://github.com/xxrrx/IoT-Project',
      iconBg: 'linear-gradient(135deg, #24292E, #57606A)',
      accent: '#24292E',
    },
    {
      id: 2,
      icon: assets.figma,
      title: 'Figma Design',
      link: 'https://www.figma.com/design/3DGC4N26TEq4SwRVH8GkGY/Web-design?node-id=0-1&t=OuSJaqP4EaKXpbXz-1',
      iconBg: 'linear-gradient(135deg, #F24E1E, #FF7262)',
      accent: '#F24E1E',
    },
    {
      id: 3,
      icon: assets.fileLinesSolid,
      title: 'Báo cáo Final',
      link: 'https://docs.google.com/document/d/1teGd6XN_qFJf01Q4_XVUuiEyiEXWwbp0G61mQIrCKgE/edit?tab=t.0',
      iconBg: 'linear-gradient(135deg, #4285F4, #2B3D81)',
      accent: '#4285F4',
    },
    {
      id: 4,
      icon: assets.fileLinesSolid,
      title: 'API Documentation',
      link: 'http://localhost:8080/swagger-ui/index.html#/',
      iconBg: 'linear-gradient(135deg, #00BFA5, #00695C)',
      accent: '#00BFA5',
    },
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
                style={{ borderLeft: `4px solid ${resource.accent}` }}
                target="_blank"
                rel="noreferrer"
              >
                <div
                  className="resource-icon"
                  style={{ background: resource.iconBg }}
                >
                  <img
                    src={resource.icon}
                    alt={resource.title}
                    style={{ filter: 'brightness(0) invert(1)' }}
                  />
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