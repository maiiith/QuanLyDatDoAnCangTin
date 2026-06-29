# Ứng Dụng Mobile: Đặt Đồ Ăn Căng Tin Trường Học

**Bài tập lớn môn học:** Lập trình Mobile cơ bản

**Giảng viên hướng dẫn:** BÙI ĐỨC THỌ 

**Lớp:** 12523T.1

---

# Giới thiệu đề tài

### Bối cảnh

Trong môi trường học đường, nhu cầu đặt món ăn tại căng tin ngày càng tăng. Tuy nhiên, việc phải xếp hàng chờ đợi vào giờ cao điểm gây mất nhiều thời gian và ảnh hưởng đến thời gian học tập của sinh viên.

### Vấn đề hiện tại

* Sinh viên phải đến trực tiếp để xem món ăn và đặt món.
* Khó quản lý số lượng đơn hàng trong giờ cao điểm.
* Việc cập nhật thực đơn và giá món ăn còn thủ công.
* Chưa có hệ thống giúp quản lý đơn hàng và món ăn hiệu quả.

### Mục tiêu / Giải pháp

Xây dựng ứng dụng Android giúp sinh viên:

* Xem danh sách món ăn.
* Thêm món vào giỏ hàng.
* Đặt món nhanh chóng.
* Theo dõi đơn hàng.

Đồng thời cung cấp phân hệ **Admin** giúp quản lý món ăn, danh mục và đơn hàng ngay trên ứng dụng bằng cơ sở dữ liệu SQLite.

---

# Thiết kế Cơ sở dữ liệu (Database)

Ứng dụng sử dụng **SQLite** để lưu trữ dữ liệu cục bộ, đảm bảo tốc độ truy vấn nhanh và hoạt động ngay cả khi không có Internet.

## Các bảng dữ liệu chính

### NguoiDung

Quản lý tài khoản người dùng và quản trị viên.

Các trường:

* id
* username
* password
* fullname
* phone
* role

---

### DanhMuc

Quản lý danh mục món ăn.

Các trường:

* id
* tenDanhMuc

---

### MonAn

Quản lý thông tin món ăn.

Các trường:

* id
* tenMon
* gia
* moTa
* hinhAnh
* soLuong
* idDanhMuc

---

### GioHang

Quản lý các món người dùng đã thêm vào giỏ hàng.

Các trường:

* id
* idMon
* soLuong
* thanhTien

---

### DonHang

Quản lý thông tin đơn hàng.

Các trường:

* id
* tenKhachHang
* ngayDat
* tongTien
* trangThai

---

### ChiTietDonHang

Lưu chi tiết các món trong từng đơn hàng.

Các trường:

* id
* idDonHang
* idMon
* soLuong
* donGia

---

# Luồng chức năng (Use Cases)

Hệ thống được xây dựng cho hai đối tượng chính.

## Người dùng (User)

### Đăng ký

Tạo tài khoản mới.

### Đăng nhập

Đăng nhập để sử dụng hệ thống.

### Xem danh sách món ăn

Hiển thị đầy đủ các món ăn theo từng danh mục.

### Xem chi tiết món ăn

Hiển thị:

* Hình ảnh
* Giá
* Mô tả
* Số lượng

### Tìm kiếm món ăn

Tìm kiếm theo tên món ăn.

### Giỏ hàng

* Thêm món
* Cập nhật số lượng
* Xóa món khỏi giỏ

### Đặt hàng

Xác nhận đơn hàng và lưu vào cơ sở dữ liệu.

### Lịch sử đơn hàng

Xem các đơn hàng đã đặt.

### Đăng xuất

Thoát khỏi hệ thống.

---

## Quản trị viên (Admin)

### Đăng nhập

Đăng nhập bằng tài khoản Admin.

### Quản lý món ăn

* Thêm món ăn
* Sửa món ăn
* Xóa món ăn
* Hiển thị danh sách món ăn

### Quản lý danh mục

* Thêm danh mục
* Sửa danh mục
* Xóa danh mục

### Quản lý đơn hàng

* Xem danh sách đơn hàng
* Cập nhật trạng thái đơn hàng
* Xóa đơn hàng

### Quản lý tài khoản

* Xem danh sách người dùng
* Đổi mật khẩu

---

# Công nghệ & Kiến trúc sử dụng

**Nền tảng**

Android (API 24 trở lên)

**Ngôn ngữ**

Java

**Môi trường phát triển**

Android Studio

**Cơ sở dữ liệu**

SQLite

**Thiết kế giao diện**

* XML Layout
* RecyclerView
* CardView
* ViewBinding
* Material Design

**Kiến trúc**

* Activity
* Adapter
* Model
* SQLiteOpenHelper
* Intent
* SharedPreferences

---

# Kết quả đạt được

* Ứng dụng hoạt động ổn định.
* Giao diện thân thiện và dễ sử dụng.
* Quản lý dữ liệu bằng SQLite.
* Hỗ trợ phân quyền User/Admin.
* Chức năng CRUD hoạt động chính xác.
* Giỏ hàng và đặt hàng hoạt động tốt.

---

# Hạn chế

* Chỉ lưu dữ liệu trên thiết bị.
* Chưa hỗ trợ thanh toán trực tuyến.
* Chưa đồng bộ dữ liệu giữa nhiều thiết bị.

---

# Hướng phát triển

* Tích hợp Firebase để đồng bộ dữ liệu.
* Thanh toán trực tuyến (VNPay, MoMo).
* Thông báo đơn hàng.
* Đánh giá món ăn.
* Yêu thích món ăn.
* Theo dõi trạng thái đơn hàng theo thời gian thực.

---

# Hướng dẫn chạy dự án

## Cài đặt môi trường

* Android Studio
* Android SDK
* Java JDK
* Máy ảo Android hoặc thiết bị thật

---

## Cách chạy

Clone project

```bash
git clone [Link GitHub của bạn]
```

Sau đó:

* Mở Android Studio.
* Chọn **Open Project**.
* Chờ Gradle đồng bộ.
* Nhấn **Run App**.

---

## Tài khoản mặc định

### Admin

Tài khoản:

```
admin
```

Mật khẩu:

```
123
```

### User

Có thể đăng ký tài khoản mới ngay trên ứng dụng.

---

# Cấu trúc thư mục dự án

```
├── app/
│   ├── src/main/java/com/example/appdatdoan/
│   │      ├── activities/
│   │      ├── adapters/
│   │      ├── database/
│   │      ├── models/
│   │      └── utils/
│   │
│   ├── src/main/res/
│   │      ├── layout/
│   │      ├── drawable/
│   │      ├── values/
│   │      └── mipmap/
│   │
│   └── AndroidManifest.xml
│
├── reports/
├── slides/
├── README.md
└── .gitignore
```

---

# Tác giả

**Họ và tên:** Lưu Thị Thanh Mai & Đỗ Thị Hằng Nga
**Lớp:** 12523T.1
**Đề tài:** Xây dựng ứng dụng Mobile Đặt Đồ Ăn Căng Tin Trường Học
**Môn học:** Lập trình Mobile cơ bản

