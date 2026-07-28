# Rule: SOLID Principles

Tất cả các file Java trong dự án phải tuân thủ SOLID principles để viết code flexible, dễ bảo trì, test, và mở rộng sau này.

---

## 1. Single Responsibility Principle (SRP)

**Định nghĩa:** Một lớp chỉ nên có 1 lý do duy nhất để thay đổi.

**Kiểm tra:** Mô tả một lớp mà phải dùng "and" (ví dụ "save AND send email") → SRP bị vi phạm. Phải mock quá nhiều dependencies khi test → có thể SRP bị vi phạm.

**Fix:** Extract mỗi trách nhiệm thành class riêng (UserRepository, EmailService, ReportGenerator, EmailValidator).

---

## 2. Open/Closed Principle (OCP)

**Định nghĩa:** Mở để kế thừa, đóng để chỉnh sửa — thêm feature mới không cần chỉnh sửa code hiện tại.

**Kiểm tra:** Khi requirement thay đổi, phải modify existing class? → OCP bị vi phạm. Có nhiều if-else check types? → Có thể vi phạm.

**Fix:** Dùng interface/inheritance. Thay vì `if (type.equals("CREDIT_CARD")) {...}` → implement `PaymentMethod` interface, tạo `CreditCardPayment`, `PayPalPayment` classes. Class `PaymentProcessor` delegate tới `PaymentMethod.process()` — thêm payment type mới chỉ cần class mới, không modify existing.

---

## 3. Liskov Substitution Principle (LSP)

**Định nghĩa:** Lớp con phải có thể thay thế lớp cha mà không thay đổi hành vi ban đầu.

**Kiểm tra:** Khi substitute parent bằng child, program break? → LSP bị vi phạm. Child throw unexpected exception? → LSP bị vi phạm.

**Fix:** Hierarchy phải phản ánh thực tế. Ví dụ: `Bird.fly()` cho tất cả bird là sai (penguin không bay). Tách `FlyingBird extends Bird` có `fly()`, `Penguin extends Bird` có `swim()`.

---

## 4. Interface Segregation Principle (ISP)

**Định nghĩa:** Client không nên depend trên interface mà chúng không dùng.

**Kiểm tra:** Implement interface nhưng throw `UnsupportedOperationException`? → ISP bị vi phạm. Có method trong interface mà không phải tất cả implementation đều dùng? → Interface quá fat.

**Fix:** Split thành smaller interfaces. Thay vì `Worker {work(), eat()}` → `Workable {work()}` và `Eatable {eat()}`. Robot implement `Workable` thôi, Human implement cả hai.

---

## 5. Dependency Inversion Principle (DIP)

**Định nghĩa:** Các module cấp cao không phụ thuộc trên module cấp thấp hơn — cả hai phụ thuộc trên abstractions.

**Kiểm tra:** Constructor khởi tạo dependencies bằng `new`? → DIP bị vi phạm. Khó mock khi test? → DIP bị vi phạm.

**Fix:** Inject dependencies. Thay vì `class UserService { private DB = new MySQL() }` → `UserService(Database db)`. Gọi khi cần `new UserService(new MySQL())` hoặc test `new UserService(new MockDB())`.

---

## Áp dụng

| Principle | Khi nào                             | Ưu tiên   |
|-----------|-------------------------------------|-----------| 
| **SRP**   | Mọi lúc                             | Cao nhất  |
| **OCP**   | Khi biết requirement thay đổi       | Cao       |
| **LSP**   | Khi dùng inheritance                | Trung     |
| **ISP**   | Design shared interfaces            | Trung     |
| **DIP**   | Multiple implementations / testing  | Cao       |

**Red flags:**
- Class tên chứa "Manager", "Handler", "Processor" → SRP?
- Phải modify existing class khi thêm feature → OCP?
- Subclass throw `UnsupportedOperationException` → LSP?
- Implement interface nhưng throw exception → ISP?
- Constructor với `new` dependencies → DIP?