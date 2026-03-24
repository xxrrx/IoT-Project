import React, { useState } from 'react'
import './Datasensor.css'
import { assets } from '../../assets/assets'

const Datasensor = () => {
  const [currentPage, setCurrentPage] = useState(1)
  const [searchTerm, setSearchTerm] = useState('')
  const [filterType, setFilterType] = useState('all')
  const [sortOrder, setSortOrder] = useState('desc')
  const [itemsPerPage, setItemsPerPage] = useState(40)
  const [showItemsDropdown, setShowItemsDropdown] = useState(false)
  const itemsOptions = [10, 20, 40, 50, 100]

  // Dữ liệu mẫu - thay thế bằng data từ API
  const sensorTypes = [
    { type: 'Nhiệt độ', value: '19 °C' },
    { type: 'Ánh sáng', value: '100 lx' },
    { type: 'Độ ẩm', value: '92 %' },
  ]
  const sensorData = Array.from({ length: 1240 }, (_, index) => ({
    id: index + 1,
    sensorType: sensorTypes[index % 3].type,
    sensorValue: sensorTypes[index % 3].value,
    timestamp: '03:05:24 17/01/2026'
  }))

  const totalPages = Math.ceil(sensorData.length / itemsPerPage)
  const startIndex = (currentPage - 1) * itemsPerPage
  const endIndex = startIndex + itemsPerPage
  const currentData = sensorData.slice(startIndex, endIndex)

  const handleRefresh = () => {
    console.log('Refreshing data...')
    // TODO: Gọi API để refresh dữ liệu
  }

  const handleExportCSV = () => {
    console.log('Exporting to CSV...')
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
    <div className="datasensor-container">
      <div className="datasensor-card">
        <h1 className="page-title">Bảng dữ liệu cảm biến</h1>
        
        <div className="controls-wrapper">
          <div className="left-controls">
            <div className="filter-dropdown">
              <select 
                value={filterType} 
                onChange={(e) => setFilterType(e.target.value)}
                className="filter-select"
              >
                <option value="all">Tất cả loại cảm biến</option>
                <option value="Nhiệt độ">Nhiệt độ</option>
                <option value="Ánh sáng">Ánh sáng</option>
                <option value="Độ ẩm">Độ ẩm</option>
              </select>
            </div>
            
            <div className="search-box">
              <img src={assets.magnifyingGlass} alt="search" className="search-icon" />
              <input 
                type="text" 
                placeholder="Tìm kiếm dữ liệu"
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
                <th>Loại cảm biến</th>
                <th>Giá trị cảm biến</th>
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
                  <td>{row.sensorType}</td>
                  <td>{row.sensorValue}</td>
                  <td>{row.timestamp}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="pagination-wrapper">
          <div className="pagination-info">
            Hiển thị {startIndex + 1}-{Math.min(endIndex, sensorData.length)} trên {sensorData.length.toLocaleString()} kết quả
          </div>
          
          <div className="pagination-controls">
            <div className="items-per-page-wrapper">
              <div
                className="items-per-page"
                onClick={() => setShowItemsDropdown(!showItemsDropdown)}
              >
                <span>{itemsPerPage} dòng</span>
                <img
                  src={assets.angleDown}
                  alt="dropdown"
                  className={showItemsDropdown ? 'rotated' : ''}
                />
              </div>
              {showItemsDropdown && (
                <ul className="items-dropdown-list">
                  {itemsOptions.map(opt => (
                    <li
                      key={opt}
                      className={`items-dropdown-item ${opt === itemsPerPage ? 'active' : ''}`}
                      onClick={() => {
                        setItemsPerPage(opt)
                        setCurrentPage(1)
                        setShowItemsDropdown(false)
                      }}
                    >
                      {opt} dòng
                    </li>
                  ))}
                </ul>
              )}
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

export default Datasensor
