# Quy tắc: Nguyên Tắc Bảo Mật

Code phải tuân theo các thực hành bảo mật tốt nhất — ngăn chặn tiêm, truy cập trái phép, rò rỉ dữ liệu, để lộ thông tin xác thực.

---

## Nguyên tắc 1: Xác Thực Và Làm Sạch Đầu Vào

**Định nghĩa:** Luôn xác thực và làm sạch dữ liệu từ bên ngoài trước khi xử lý.

**Kiểm tra:**
- Đưa thẳng dữ liệu người dùng nhập vào câu lệnh SQL mà không qua kiểm tra? → Kẻ xấu có thể chèn câu lệnh SQL độc hại vào để đọc/xóa dữ liệu (tấn công SQL Injection)
- Không giới hạn kích thước hoặc kiểm tra định dạng file khi cho phép tải lên? → Hệ thống có thể bị làm quá tải (tấn công từ chối dịch vụ) hoặc bị tải lên file chứa mã độc
- Nhận bất kỳ dữ liệu nào từ request API rồi tự động chuyển thành object mà không kiểm soát? → Kẻ tấn công có thể lợi dụng cơ chế này để chạy mã tùy ý trên server
- Không giới hạn độ dài hoặc kiểm tra định dạng chuỗi đầu vào? → Có thể gây tràn bộ nhớ hoặc bị lợi dụng để chèn mã độc

**Cách sửa:**
- Danh sách cho phép (cho phép những gì hợp lệ) thay vì danh sách cấm
- Chuyển đổi loại và xác thực (số nguyên, email, định dạng điện thoại)
- Giới hạn độ dài và kiểm tra mã hóa
- Dùng câu lệnh chuẩn bị trước (SQL), truy vấn tham số hóa

---

## Nguyên tắc 2: Quyền Hạn Tối Thiểu

**Định nghĩa:** Code chỉ nên có quyền hạn tối thiểu cần thiết.

**Kiểm tra:**
- Tất cả method trong class đều để `public`, kể cả những method chỉ dùng nội bộ? → Bên ngoài có thể gọi vào những chỗ lẽ ra không nên đụng tới
- Tất cả trường (field) đều để `public`? → Bất kỳ đoạn code nào cũng sửa được trực tiếp, không qua kiểm soát
- Tài khoản dịch vụ (service account) chạy với quyền quản trị (admin)? → Nếu tài khoản đó bị chiếm, kẻ tấn công có toàn quyền hệ thống
- Tài khoản kết nối cơ sở dữ liệu có đủ quyền CHỌN+CHÈN+CẬP NHẬT+XÓA trên TẤT CẢ các bảng, kể cả bảng không liên quan? → Rủi ro lớn nếu tài khoản đó bị lộ hoặc code có lỗi

**Cách sửa:**
- Tạo method/trường private/protected (mặc định nên private)
- Dùng quyền truy cập chỉ đọc khi có thể
- Cấp cho người dùng cơ sở dữ liệu quyền trên bảng/cột cụ thể
- Tài khoản dịch vụ: vai trò cụ thể, không phải quản trị

---

## Nguyên tắc 3: Không Mã Cứng Bí Mật

**Định nghĩa:** Thông tin xác thực, khóa API, mật khẩu không bao giờ được mã cứng.

**Kiểm tra:**
- Mật khẩu hoặc khóa API được viết thẳng thành chuỗi ký tự trong code? → Ai đọc được source code là đọc được luôn mật khẩu/khóa đó
- Thông tin xác thực nằm trong file cấu hình rồi bị commit lên git? → Bất kỳ ai có quyền xem repo (kể cả trong lịch sử commit cũ) đều thấy được
- URL kết nối cơ sở dữ liệu có sẵn user:password ngay trong chuỗi kết nối? → Cùng vấn đề, dễ bị lộ theo code
- Khóa riêng tư (private key) được lưu trong file mã nguồn? → Nếu source code bị lộ, khóa riêng tư cũng bị lộ theo

**Cách sửa:**
- Biến môi trường (dev/staging/prod khác nhau)
- Công cụ quản lý bí mật (AWS Secrets Manager, HashiCorp Vault)
- File `.env` (gitignore)
- File cấu hình bên ngoài kho lưu trữ (triển khai riêng)
- Thay đổi thông tin xác thực thường xuyên

---

## Nguyên tắc 4: Xác Thực Và Ủy Quyền

**Định nghĩa:** Xác minh danh tính (xác thực), sau đó kiểm tra quyền (ủy quyền).

**Kiểm tra:**
- Thao tác nhạy cảm (xóa dữ liệu, đổi mật khẩu...) không yêu cầu đăng nhập? → Bất kỳ ai cũng gọi được, không cần biết là ai
- Người dùng A có thể sửa được dữ liệu của người dùng B? → Có kiểm tra danh tính (ai đang gọi) nhưng thiếu kiểm tra quyền (họ có được phép làm việc đó không)
- Phiên đăng nhập (session) không tự hết hạn? → Nếu phiên đó bị đánh cắp, kẻ xấu dùng được vô thời hạn
- Không có token chống giả mạo yêu cầu (CSRF token)? → Kẻ tấn công có thể lừa trình duyệt người dùng gửi yêu cầu giả mà họ không hề biết
- Token JWT không đặt thời gian hết hạn? → Token bị đánh cắp vẫn dùng được mãi mãi, không có cách nào vô hiệu hóa

**Cách sửa:**
- Yêu cầu xác thực trước khi thực hiện thao tác nhạy cảm
- Kiểm tra quyền/sở hữu người dùng trước khi cho phép
- Hết hạn phiên (có thể cấu hình)
- Token CSRF cho các yêu cầu thay đổi trạng thái
- Hết hạn token và token làm mới

---

## Nguyên tắc 5: Mã Hóa Đầu Ra

**Định nghĩa:** Mã hóa đầu ra dựa trên ngữ cảnh (HTML, URL, JavaScript, SQL).

**Kiểm tra:**
- Hiển thị dữ liệu do người dùng nhập thẳng lên trang HTML mà không mã hóa? → Kẻ tấn công có thể chèn đoạn mã JavaScript độc hại chạy trên trình duyệt người khác (tấn công XSS)
- Ghép câu lệnh SQL bằng cách nối chuỗi trực tiếp thay vì dùng tham số? → Dễ bị tấn công SQL Injection
- Ghi mật khẩu/token vào log? → Dữ liệu nhạy cảm bị lộ nếu ai đó đọc được log

**Cách sửa:**
- Mã hóa HTML: `&lt;`, `&quot;`, v.v. (dùng hàm framework)
- Mã hóa URL: `%20`, `%3D`, v.v.
- SQL: dùng truy vấn tham số hóa (không bao giờ nối chuỗi)
- Mức nhật ký thích hợp (không DEBUG cho môi trường sản xuất)

---

## Nguyên tắc 6: Mã Hóa Và Băm

**Định nghĩa:** Dữ liệu nhạy cảm phải được mã hóa (khi lưu trữ và khi truyền). Mật khẩu phải được băm.

**Kiểm tra:**
- Cơ sở dữ liệu lưu mật khẩu người dùng dưới dạng văn bản thô (không mã hóa)? → Chỉ cần cơ sở dữ liệu bị rò rỉ 1 lần là toàn bộ mật khẩu người dùng lộ hết
- Dùng HTTP thay vì HTTPS? → Dữ liệu truyền đi không được mã hóa, kẻ tấn công đứng giữa đường truyền (ví dụ trên cùng mạng wifi) có thể nghe lén hoặc đánh cắp dữ liệu (tấn công man-in-the-middle)
- Dùng thuật toán băm MD5/SHA1 cho mật khẩu? → Đây là thuật toán cũ, máy tính hiện đại có thể dò ngược ra mật khẩu gốc khá nhanh
- Khóa dùng để mã hóa lại được viết cứng trong code? → Nếu code bị lộ thì khóa mã hóa cũng lộ theo, coi như không còn mã hóa nữa

**Cách sửa:**
- HTTPS ở mọi nơi (TLS 1.2+)
- Dùng bcrypt/scrypt/Argon2 để băm mật khẩu (không MD5/SHA1)
- Mã hóa các trường nhạy cảm trong cơ sở dữ liệu (mã hóa khi lưu trữ)
- Khóa mã hóa trong quản lý bí mật, thay đổi thường xuyên
- Tự động thêm muối cho mật khẩu (bcrypt làm điều này)

---

## Nguyên tắc 7: Xử Lý Lỗi Và Ghi Nhật Ký

**Định nghĩa:** Thông báo lỗi thân thiện với người dùng, chi tiết cho nhật ký. Không bao giờ để lộ nội bộ.

**Kiểm tra:**
- Khi có lỗi, trả nguyên dấu vết ngăn xếp (stack trace) kỹ thuật cho người dùng xem? → Kẻ tấn công có thể lợi dụng thông tin đó (đường dẫn file, tên class, phiên bản thư viện) để tìm điểm yếu hệ thống
- Ghi mật khẩu hoặc token vào file log? → Ai đọc được log là đọc được thông tin nhạy cảm đó
- Không ghi lại các sự kiện liên quan bảo mật (đăng nhập sai nhiều lần, truy cập trái phép)? → Không có dữ liệu để phát hiện khi hệ thống đang bị tấn công
- Dùng thông báo "Lỗi" chung chung y hệt nhau ở mọi chỗ? → Nhà phát triển khó tra ra nguyên nhân thật sự khi gỡ lỗi

**Cách sửa:**
- Thông báo lỗi chung chung cho người dùng ("Đã có lỗi xảy ra")
- Nhật ký chi tiết cho nhà phát triển (bao gồm ngữ cảnh, KHÔNG bí mật)
- Ghi nhật ký các lỗi xác thực, truy cập trái phép, vi phạm bảo mật
- Ghi nhật ký có cấu trúc (JSON) để phân tích
- Giám sát nhật ký để tìm các mẫu đáng ngờ

---

## Nguyên tắc 8: Quản Lý Phụ Thuộc

**Định nghĩa:** Cập nhật phụ thuộc — vá các lỗ hổng bảo mật đã biết.

**Kiểm tra:**
- Dự án đang dùng phiên bản thư viện cũ, chưa từng cập nhật? → Có thể đang tồn tại lỗ hổng đã được công khai và vá từ lâu
- Chưa từng chạy công cụ quét lỗ hổng cho các thư viện đang dùng? → Không biết dự án đang có lỗ hổng gì đang tồn tại
- Tắt cơ chế cập nhật tự động cho dependencies? → Dự án dần lạc hậu, càng để lâu càng khó cập nhật (do các thay đổi lớn dồn lại)

**Cách sửa:**
- Cập nhật phụ thuộc thường xuyên (Maven, npm, gradle, pip)
- Dùng công cụ quét lỗ hổng (OWASP Dependency-Check, Snyk)
- Cập nhật phiên bản vá thường xuyên (1.0.0 → 1.0.5)
- Giám sát các cảnh báo bảo mật cho thư viện được dùng
- Kiểm thử sau cập nhật (kiểm thử hồi quy)

---

## Dấu hiệu cảnh báo

1. Mật khẩu/khóa API nằm thẳng trong code hoặc file cấu hình → **NGHIÊM TRỌNG**, cần xử lý ngay
2. Không kiểm tra/xác thực dữ liệu đầu vào từ người dùng → dễ bị chèn mã độc
3. Toàn bộ method/trường của class đều để `public` → để lộ quá nhiều, ai cũng gọi/sửa được
4. Câu lệnh SQL được ghép bằng cách nối chuỗi trực tiếp → dễ bị tấn công SQL Injection
5. Không dùng HTTPS mà dùng HTTP → dữ liệu truyền đi không mã hóa, dễ bị nghe lén giữa đường truyền
6. Mật khẩu lưu dạng văn bản thô hoặc băm bằng MD5 → dễ bị dò ngược ra mật khẩu gốc
7. Trả nguyên dấu vết ngăn xếp kỹ thuật cho người dùng khi có lỗi → lộ thông tin nội bộ hệ thống
8. Ghi dữ liệu nhạy cảm (mật khẩu, token) vào log → log bị lộ là dữ liệu đó cũng lộ theo
9. Phiên đăng nhập (session) không có thời gian hết hạn → nếu bị đánh cắp, kẻ xấu dùng được mãi mãi
10. Thư viện/phụ thuộc dùng phiên bản cũ, không cập nhật → tồn tại lỗ hổng bảo mật đã công khai, ai cũng biết cách khai thác

---

## Trước khi triển khai

- Kiểm duyệt code để tìm vấn đề bảo mật
- Chạy công cụ quét lỗ hổng trên phụ thuộc
- Kiểm thử xâm nhập (nếu có thể)
- Danh sách kiểm tra kiểm toán bảo mật
- Thay đổi bí mật, chứng chỉ
- Bật giám sát/cảnh báo bảo mật