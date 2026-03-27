import React, { useState, useEffect } from 'react'
import './Datasensor.css'
import { assets } from '../../assets/assets'

const SENSOR_TYPE_LABEL = { light: 'Ánh sáng', temperature: 'Nhiệt độ', humidity: 'Độ ẩm' }
const SENSOR_UNIT = { light: 'lx', temperature: '°C', humidity: '%' }

const formatDateTime = (iso) => {
  const d = new Date(iso)
  const pad = n => String(n).padStart(2, '0')
  return `${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())} ${pad(d.getDate())}/${pad(d.getMonth() + 1)}/${d.getFullYear()}`
}

const Datasensor = () => {
  const itemsOptions = [10, 20, 40, 50, 100]
  const [showItemsDropdown, setShowItemsDropdown] = useState(false)
  // Pending filter inputs (chưa gửi đi)
  const [pendingSensorType, setPendingSensorType] = useState('')
  const [pendingTime, setPendingTime] = useState('')

  // Query state — thay đổi object này để trigger useEffect gọi API
  const [query, setQuery] = useState({
    page: 1,
    size: 40,
    sensorType: '',
    time: '',
    sortBy: 'descending',
  })

  // Dữ liệu trả về
  const [data, setData] = useState([])
  const [totalElements, setTotalElements] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [loading, setLoading] = useState(false)
  const [exporting, setExporting] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    const controller = new AbortController()

    const load = async () => {
      setLoading(true)
      setError(null)
      try {
        const params = new URLSearchParams({
          page: query.page - 1, // API dùng 0-indexed
          size: query.size,
          sortBy: query.sortBy,
        })
        if (query.sensorType) params.append('sensorType', query.sensorType)
        if (query.time) params.append('time', query.time)

        const res = await fetch(`http://localhost:8080/data-sensor?${params}`, {
          signal: controller.signal,
        })
        const json = await res.json()

        if (!res.ok) {
          setError(json.message || 'Có lỗi xảy ra khi tải dữ liệu')
          setData([])
          setTotalElements(0)
          setTotalPages(0)
          return
        }

        setData(json.content || [])
        setTotalElements(json.totalElements || 0)
        setTotalPages(json.totalPages || 0)
      } catch (err) {
        if (err.name !== 'AbortError') {
          setError('Không thể kết nối đến server')
          setData([])
          setTotalElements(0)
          setTotalPages(0)
        }
      } finally {
        setLoading(false)
      }
    }

    load()
    return () => controller.abort()
  }, [query])

  // Bấm Tìm kiếm — áp dụng filter đang nhập, reset về trang 1
  const handleSearch = () => {
    setQuery(prev => ({
      ...prev,
      page: 1,
      sensorType: pendingSensorType,
      time: pendingTime,
    }))
  }

  // Làm mới — tạo object mới để trigger useEffect với cùng params
  const handleRefresh = () => {
    setQuery(prev => ({ ...prev }))
  }

  // Xuất CSV toàn bộ bản ghi với filter và sort hiện tại
  const handleExportCSV = async () => {
    if (totalElements === 0) return
    setExporting(true)
    setError(null)
    try {
      const params = new URLSearchParams({
        page: 0,
        size: totalElements,
        sortBy: query.sortBy,
      })
      if (query.sensorType) params.append('sensorType', query.sensorType)
      if (query.time) params.append('time', query.time)

      const res = await fetch(`http://localhost:8080/data-sensor?${params}`)
      const json = await res.json()

      if (!res.ok) {
        setError(json.message || 'Có lỗi xảy ra khi xuất dữ liệu')
        return
      }

      const allRecords = json.content || []
      const headers = ['STT', 'Loại cảm biến', 'Giá trị', 'Đơn vị', 'Thời gian']
      const rows = allRecords.map((row, i) => [
        i + 1,
        SENSOR_TYPE_LABEL[row.sensorType] || row.sensorType,
        row.value,
        SENSOR_UNIT[row.sensorType] || '',
        formatDateTime(row.recordAt),
      ])
      const csv = [headers, ...rows]
        .map(r => r.map(c => `"${c}"`).join(','))
        .join('\n')
      const blob = new Blob(['\uFEFF' + csv], { type: 'text/csv;charset=utf-8;' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `sensor-data-all.csv`
      a.click()
      URL.revokeObjectURL(url)
    } catch {
      setError('Không thể kết nối đến server khi xuất dữ liệu')
    } finally {
      setExporting(false)
    }
  }

  const startIndex = (query.page - 1) * query.size

  const renderPageNumbers = () => {
    const pages = []
    if (totalPages <= 5) {
      for (let i = 1; i <= totalPages; i++) pages.push(i)
    } else if (query.page <= 3) {
      pages.push(1, 2, 3, '...', totalPages)
    } else if (query.page >= totalPages - 2) {
      pages.push(1, '...', totalPages - 2, totalPages - 1, totalPages)
    } else {
      pages.push(1, '...', query.page - 1, query.page, query.page + 1, '...', totalPages)
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
                value={pendingSensorType}
                onChange={e => setPendingSensorType(e.target.value)}
                className="filter-select"
              >
                <option value="">Tất cả cảm biến</option>
                <option value="temperature">Nhiệt độ</option>
                <option value="light">Ánh sáng</option>
                <option value="humidity">Độ ẩm</option>
              </select>
            </div>

            <div className="search-box">
              <img src={assets.magnifyingGlass} alt="search" className="search-icon" />
              <input
                type="text"
                placeholder="Thời gian (VD: 2026/03/27)"
                value={pendingTime}
                onChange={e => setPendingTime(e.target.value)}
                className="search-input"
                onKeyDown={e => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <button className="btn-search" onClick={handleSearch}>
              Tìm kiếm
            </button>
          </div>

          <div className="right-controls">
            <button className="btn-refresh" onClick={handleRefresh}>
              <img src={assets.arrowsRotate} alt="refresh" />
              Làm mới
            </button>
            <button className="btn-export" onClick={handleExportCSV} disabled={totalElements === 0 || exporting}>
              <img src={assets.fileExport} alt="export" />
              {exporting ? 'Đang xuất...' : 'Xuất file csv'}
            </button>
          </div>
        </div>

        {error && <div className="error-message">{error}</div>}

        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>STT</th>
                <th>Loại cảm biến</th>
                <th>Giá trị cảm biến</th>
                <th>Thời gian</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr>
                  <td colSpan={4} className="loading-cell">Đang tải dữ liệu...</td>
                </tr>
              ) : data.length === 0 ? (
                <tr>
                  <td colSpan={4} className="empty-cell">Không có dữ liệu</td>
                </tr>
              ) : (
                data.map((row, index) => (
                  <tr key={index}>
                    <td>{startIndex + index + 1}</td>
                    <td>{SENSOR_TYPE_LABEL[row.sensorType] || row.sensorType}</td>
                    <td>{row.value} {SENSOR_UNIT[row.sensorType] || ''}</td>
                    <td>{formatDateTime(row.recordAt)}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        <div className="pagination-wrapper">
          <div className="pagination-info">
            {totalElements > 0
              ? `Hiển thị ${startIndex + 1}–${Math.min(startIndex + query.size, totalElements)} trên ${totalElements.toLocaleString()} kết quả`
              : 'Không có kết quả'
            }
          </div>

          <div className="pagination-controls">
            <div className="items-per-page-wrapper">
              <div
                className="items-per-page"
                onClick={() => setShowItemsDropdown(!showItemsDropdown)}
              >
                <span>{query.size} dòng</span>
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
                      className={`items-dropdown-item ${opt === query.size ? 'active' : ''}`}
                      onClick={() => {
                        setQuery(prev => ({ ...prev, page: 1, size: opt }))
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
              onClick={() => setQuery(prev => ({ ...prev, page: prev.page - 1 }))}
              disabled={query.page === 1}
            >
              ‹
            </button>

            {renderPageNumbers().map((page, index) =>
              page === '...' ? (
                <span key={`ellipsis-${index}`} className="page-ellipsis">...</span>
              ) : (
                <button
                  key={page}
                  className={`page-number ${query.page === page ? 'active' : ''}`}
                  onClick={() => setQuery(prev => ({ ...prev, page }))}
                >
                  {page}
                </button>
              )
            )}

            <button
              className="page-nav"
              onClick={() => setQuery(prev => ({ ...prev, page: prev.page + 1 }))}
              disabled={query.page === totalPages || totalPages === 0}
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

