# IOT Smart Home Dashboard

Hệ thống giám sát và điều khiển thiết bị IoT theo thời gian thực, bao gồm dashboard trực quan, bảng dữ liệu cảm biến, lịch sử hành động và điều khiển thiết bị qua MQTT.

---

## Tính năng chính

- **Dashboard thời gian thực** — Biểu đồ nhiệt độ, độ ẩm, ánh sáng cập nhật liên tục qua SSE
- **Điều khiển thiết bị** — Bật/tắt 3 thiết bị (LED1, LED2, LED3) qua MQTT
- **Bảng dữ liệu cảm biến** — Phân trang, lọc theo loại cảm biến & thời gian, xuất CSV
- **Lịch sử hành động** — Tra cứu theo thiết bị, hành động, thời gian, xuất CSV
- **Kết nối MQTT** — Nhận dữ liệu từ ESP32, lưu vào MySQL, đẩy lên frontend qua SSE

---

## Công nghệ sử dụng

### Frontend
| Công nghệ | Phiên bản | Mục đích |
|---|---|---|
| React | 19.2.0 | UI framework |
| Vite | 7.2.4 | Build tool |
| React Router DOM | 7.13.0 | Routing |
| Recharts | 3.7.0 | Biểu đồ |

### Backend
| Công nghệ | Phiên bản | Mục đích |
|---|---|---|
| Spring Boot | 4.0.3 | REST API framework |
| Java | 17 | Ngôn ngữ |
| MySQL | — | Cơ sở dữ liệu |
| JPA / Hibernate | — | ORM |
| HiveMQ MQTT Client | 1.3.9 | Kết nối MQTT |
| SpringDoc OpenAPI | 2.8.0 | Swagger UI |

---

## Kiến trúc hệ thống

```
┌─────────────────────────────────────────────────────────────┐
│                         ESP32                               │
│           (Cảm biến nhiệt độ / độ ẩm / ánh sáng)           │
└──────────────────────────┬──────────────────────────────────┘
                           │ MQTT (SSL :8883)
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              HiveMQ Cloud Broker                             │
│         b069ed9f4ad24e53ab13d415211da7bb.s1.eu.hivemq.cloud │
└──────────────────────────┬──────────────────────────────────┘
                           │
            ┌──────────────┴──────────────┐
            │ Subscribe: iot/sensor/data  │
            │ Subscribe: esp32/status     │
            │ Publish:   device/control   │
            ▼
┌─────────────────────────────────────────────────────────────┐
│                  Spring Boot Backend (:8080)                 │
│                                                             │
│  MqttSubscriber → SensorDataService → MySQL (iot_system)    │
│                 ↘ SseEmitterService                         │
│                       ↓ SSE stream                          │
│  REST API: /data-sensor, /action-history, /dashboard        │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP / SSE
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                  React Frontend (:5173)                      │
│                                                             │
│  Dashboard | DataSensor | ActionHistory | Profile           │
└─────────────────────────────────────────────────────────────┘
```

---

## Cấu trúc thư mục

```
IOT-Project/
├── Frontend/
│   └── src/
│       ├── pages/
│       │   ├── Dashboard/          # Biểu đồ thời gian thực + điều khiển thiết bị
│       │   ├── DataSensor/         # Bảng dữ liệu cảm biến
│       │   ├── ActionHistory/      # Lịch sử hành động
│       │   └── Profile/            # Thông tin cá nhân
│       ├── components/
│       │   ├── Chart/              # Recharts biểu đồ
│       │   ├── Navbar/             # Thanh điều hướng trên
│       │   ├── SideBar/            # Thanh điều hướng bên
│       │   ├── SensorOverview/     # Card tổng quan cảm biến
│       │   └── SwitchCard/         # Nút bật/tắt thiết bị
│       └── Context/AppContext.jsx  # Global state + SSE + polling
│
└── backend/
    └── src/main/java/com/example/iot/
        ├── controller/             # REST Controllers
        ├── service/                # Business logic
        ├── repository/             # JPA Repositories
        ├── domain/
        │   ├── entities/           # Device, Sensors, SensorData, ActionHistory
        │   ├── dto/                # Request/Response DTOs
        │   └── enums/              # DeviceStatus, SensorType
        ├── mqtt/                   # MqttConnect, MqttSubscriber
        └── config/                 # CORS, Jackson, TimeValidate
```

---

## Cài đặt và chạy dự án

### Yêu cầu
- Node.js >= 18
- Java 17
- Maven
- MySQL

### 1. Cấu hình Database

Tạo database MySQL:
```sql
CREATE DATABASE iot_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Cập nhật thông tin kết nối trong `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/iot_system
spring.datasource.username=root
spring.datasource.password=your_password
```

### 2. Chạy Backend

```bash
cd backend
mvn spring-boot:run
```

Backend khởi động tại: `http://localhost:8080`

### 3. Chạy Frontend

```bash
cd Frontend
npm install
npm run dev
```

Frontend khởi động tại: `http://localhost:5173`

---

## API Documentation

Sau khi backend chạy, truy cập Swagger UI tại:

```
http://localhost:8080/swagger-ui/index.html
```

### Danh sách endpoints

#### Dashboard
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/dashboard/chart?quantity=N` | Lấy N bản ghi cảm biến gần nhất theo từng loại |
| GET | `/dashboard/sensor/stream` | SSE stream dữ liệu cảm biến thời gian thực |
| GET | `/dashboard/device-state` | Trạng thái hiện tại của tất cả thiết bị |
| POST | `/dashboard/device-control?led=X&state=ON\|OFF` | Điều khiển bật/tắt thiết bị |

#### Data Sensor
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/data-sensor` | Lấy dữ liệu cảm biến có phân trang, lọc, sắp xếp |

**Query params:**
| Param | Bắt buộc | Mô tả |
|---|---|---|
| `page` | Có | Số trang (0-indexed) |
| `size` | Có | Số bản ghi mỗi trang |
| `sensorType` | Không | `temperature` \| `humidity` \| `light` |
| `time` | Không | Xem định dạng thời gian bên dưới |
| `sortBy` | Không | `ascending` \| `descending` (mặc định: descending) |

#### Action History
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/action-history` | Lấy lịch sử hành động có phân trang, lọc, sắp xếp |

**Query params:**
| Param | Bắt buộc | Mô tả |
|---|---|---|
| `pageNo` | Có | Số trang (0-indexed) |
| `pageSize` | Có | Số bản ghi mỗi trang |
| `deviceName` | Không | `led1` \| `led2` \| `led3` |
| `deviceAction` | Không | `ON` \| `OFF` |
| `time` | Không | Xem định dạng thời gian bên dưới |
| `sortBy` | Không | `ascending` \| `descending` (mặc định: descending) |

### Định dạng tham số `time`

| Định dạng | Ví dụ | Lọc theo |
|---|---|---|
| `YYYY` | `2026` | Cả năm |
| `YYYY/MM` | `2026/03` | Cả tháng |
| `YYYY/MM/DD` | `2026/03/27` | Cả ngày |
| `YYYY/MM/DD HH` | `2026/03/27 14` | Cả giờ |
| `YYYY/MM/DD HH/MM` | `2026/03/27 14/30` | Cả phút |
| `YYYY/MM/DD HH/MM/SS` | `2026/03/27 14/30/00` | Cả giây |

---

## MQTT

### Broker
- **Host:** `b069ed9f4ad24e53ab13d415211da7bb.s1.eu.hivemq.cloud`
- **Port:** `8883` (SSL)
- **Protocol:** MQTT 5.0

### Topics
| Topic | Hướng | Mô tả |
|---|---|---|
| `iot/sensor/data` | Subscribe | Nhận dữ liệu cảm biến từ ESP32 |
| `esp32/status` | Subscribe | Trạng thái thiết bị từ ESP32 |
| `device/control` | Publish | Gửi lệnh điều khiển xuống ESP32 |

---

## Database Schema

```
Device
├── id (UUID)
├── name (String)
├── currentStatus (ON | OFF | PENDING)
└── createAt (LocalDateTime)

Sensors
├── id (UUID)
├── name (String)
├── sensorType (temperature | humidity | light)
├── unit (String)
└── createAt (LocalDateTime)

SensorData
├── id (UUID)
├── value (Float)
├── recordAt (LocalDateTime)
└── sensor_id (FK → Sensors)

ActionHistory
├── id (UUID)
├── action (String)
├── status (String)
├── performedAt (LocalDateTime)
└── device_id (FK → Device)
```

---
