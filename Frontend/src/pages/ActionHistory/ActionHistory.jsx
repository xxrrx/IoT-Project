import React, { useState } from 'react'
import './ActionHistory.css'
import { assets } from '../../assets/assets'

const ActionHistory = () => {
  const [currentPage, setCurrentPage] = useState(1)
  const [searchTerm, setSearchTerm] = useState('')
  const [deviceFilter, setDeviceFilter] = useState('all')
  const [statusFilter, setStatusFilter] = useState('all')
  const [sortOrder, setSortOrder] = useState('desc')
  const itemsPerPage = 40

  // Dữ liệu mẫu - thay thế bằng data từ API
  const actionHistory = Array.from({ length: 1240 }, (_, index) => {
    const devices = ['Đèn điện', 'Điều hòa', 'Quạt điện']
    const actions = ['On', 'Off']
    const device = devices[Math.floor(Math.random() * devices.length)]
    const action = actions[Math.floor(Math.random() * actions.length)]
    
    return {
      id: index + 1,
      device: device,
      action: action,
      timestamp: '03:05:24 17/01/2026'
    }
  })

  const totalPages = Math.ceil(actionHistory.length / itemsPerPage)
  const startIndex = (currentPage - 1) * itemsPerPage
  const endIndex = startIndex + itemsPerPage
  const currentData = actionHistory.slice(startIndex, endIndex)

  const handleRefresh = () => {
    console.log('Refreshing action history...')
    // TODO: Gọi API để refresh dữ liệu
  }

  const handleExportCSV = () => {
    console.log('Exporting action history to CSV...')
    // TODO: Xuất dữ liệu ra file CSV
  }

  const renderPageNumbers = () => {
    const pages = []
    const maxVisiblePages = 5
    
    if (totalPages <= maxVisiblePages) {
      for (let i = 1; i <= totalPages; i++) {
        pages.push(i)
      }
    } else {
      if (currentPage <= 3) {
        pages.push(1, 2, 3, '...', totalPages)
      } else if (currentPage >= totalPages - 2) {
        pages.push(1, '...', totalPages - 2, totalPages - 1, totalPages)
      } else {
        pages.push(1, '...', currentPage - 1, currentPage, currentPage + 1, '...', totalPages)
      }
    }
    
    return pages
  }

  return (
    <div className="action-history-container">
      <div className="action-history-card">
        <h1 className="page-title">Bảng lịch sử hành động</h1>
        
        <div className="controls-wrapper">
          <div className="left-controls">
            <div className="filter-dropdown">
              <select 
                value={deviceFilter} 
                onChange={(e) => setDeviceFilter(e.target.value)}
                className="filter-select"
              >
                <option value="all">Tên thiết bị</option>
                <option value="light">Đèn điện</option>
                <option value="airconditioner">Điều hòa</option>
                <option value="fan">Quạt điện</option>
              </select>
            </div>
            
            <div className="filter-dropdown">
              <select 
                value={statusFilter} 
                onChange={(e) => setStatusFilter(e.target.value)}
                className="filter-select"
              >
                <option value="all">Trạng thái thiết bị</option>
                <option value="on">On</option>
                <option value="off">Off</option>
              </select>
            </div>
            
            <div className="search-box">
              <img src={assets.magnifyingGlass} alt="search" className="search-icon" />
              <input 
                type="text" 
                placeholder="Thời gian tìm kiếm dữ liệu"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="search-input"
              />
            </div>
          </div>
          
          <div className="right-controls">
            <button className="btn-refresh" onClick={handleRefresh}>
              <img src={assets.arrowsRotate} alt="refresh" />
              Làm mới
            </button>
            <button className="btn-export" onClick={handleExportCSV}>
              <img src={assets.fileExport} alt="export" />
              Xuất file csv
            </button>
          </div>
        </div>

        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>STT</th>
                <th>Thiết bị</th>
                <th>Hành động</th>
                <th className="sortable" onClick={() => setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc')}>
                  Thời gian
                  <img src={assets.angleDown} alt="sort" className={`sort-icon ${sortOrder}`} />
                </th>
              </tr>
            </thead>
            <tbody>
              {currentData.map((row, index) => (
                <tr key={row.id}>
                  <td>{startIndex + index + 1}</td>
                  <td>{row.device}</td>
                  <td>
                    <span className={`action-badge ${row.action.toLowerCase()}`}>
                      {row.action}
                    </span>
                  </td>
                  <td>{row.timestamp}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="pagination-wrapper">
          <div className="pagination-info">
            Hiển thị {startIndex + 1}-{Math.min(endIndex, actionHistory.length)} trên {actionHistory.length.toLocaleString()} kết quả
          </div>
          
          <div className="pagination-controls">
            <div className="items-per-page">
              <span>{itemsPerPage} dòng</span>
              <img src={assets.angleDown} alt="dropdown" />
            </div>
            
            <button 
              className="page-nav"
              onClick={() => setCurrentPage(prev => Math.max(1, prev - 1))}
              disabled={currentPage === 1}
            >
              ‹
            </button>
            
            {renderPageNumbers().map((page, index) => (
              page === '...' ? (
                <span key={`ellipsis-${index}`} className="page-ellipsis">...</span>
              ) : (
                <button
                  key={page}
                  className={`page-number ${currentPage === page ? 'active' : ''}`}
                  onClick={() => setCurrentPage(page)}
                >
                  {page}
                </button>
              )
            ))}
            
            <button 
              className="page-nav"
              onClick={() => setCurrentPage(prev => Math.min(totalPages, prev + 1))}
              disabled={currentPage === totalPages}
            >
              ›
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ActionHistory
