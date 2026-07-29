---
name: conventional-commit
description: Phân tích các thay đổi code (git diff), kiểm tra an toàn và tạo commit message đúng chuẩn Conventional Commits.
user-invocable: false
---

# Quy trình thực thi Smart Commit

1. **Kiểm tra An toàn & Rác (Pre-check):**
   - Kiểm tra toàn bộ file đang nằm trong Staged Changes (`git diff --cached`).
   - **Cảnh báo ngay nếu phát hiện:** File cấu hình nhạy cảm (`.env`, private keys), token, hoặc các câu lệnh debug thừa (như `console.log`, `print`, `debugger`).

2. **Phân tích Nội dung Thay đổi:**
   - Xác định loại thay đổi chính: `feat` (tính năng), `fix` (sửa lỗi), `refactor` (tối ưu code), `docs` (tài liệu), `chore` (cấu hình/thư viện)...
   - Xác định phạm vi ảnh hưởng (Scope): ví dụ `(auth)`, `(checkout)`, `(api)`.

3. **Soạn thảo Commit Message:**
   - Áp dụng định dạng: `<type>(<scope>): <mô tả ngắn gọn bằng tiếng Anh/Việt>`
   - Dùng thì hiện tại, không viết hoa chữ đầu câu lệnh, không dấu chấm cuối câu (VD: `feat(auth): add JWT token refresh mechanism`).
   - Nếu thay đổi lớn, bổ sung phần giải thích chi tiết (Body) bên dưới.

4. **Trả kết quả:**
   - Xuất câu lệnh `git commit -m "..."` hoàn chỉnh để người dùng duyệt trước khi chạy.