Tôi muốn làm 1 project iot sử dụng esp32 cảm biến nhiệt độ, độ ẩm và cảm biến ánh sáng để thu thập thông tin môi trường sau đó gửi dữ liệu thu thập được về backend và hiển thị lên trên frontend là 1 website, frontend có thể bật tắt các thiết bị như là bóng đèn hoặc quạt. Tôi đã liệt kê ra có 2 phần cần thực hiện đó là phần hardware, bao gồm các mạch, cảm biến và các thiết bị như đèn và quạt, phần software, bao gồm frontend, backend, database, middleware. Ở phần website có những trang chức năng là:
- Dashboard hiển thị những thông tin đang thu thập được của cảm biến và có 3 nút để bật tắt các thiết bị
- Trang Data Sensor có 1 form data có các cột như id, nhiệt độ, độ ẩm ánh sáng, và thời gian thu thập dữ liệu đó, có phân trang, có tìm kiếm( theo loại cảm biến hoặc theo thời gian)
- Trang action history lưu lại các action của người dùng và trạng thái của các thiết bị bao gồm 1 form data có các cột như stt, tên thiết bị hành động đã thực hiện, trạng thái của thiết bị sau khi thực hiện hành động, thời gian thực hiện hành động ngoài ra còn cho phép người dùng có thể tìm kiếm thiết bị theo thời gian, và lọc để hiên thị theo tên của các thiết bị hoặc lọc để hiển thị theo trạng thái, có phân trang


Hiện tại có các luồng như sau:
- Luồng hiển thị dữ liệu cảm biến