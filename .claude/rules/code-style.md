# Rule: Coding Style Guide

## 1. Nguyên tắc chung

- Khung mẫu chuẩn: [Java Code Conventions (Oracle)](https://www.oracle.com/a/tech/docs/java/codeconventions.pdf).
- Chỉ được điều chỉnh convention khi có yêu cầu từ **người sở hữu repo (Myrlennia)**.

## 2. Yêu cầu định dạng file Java

Mọi source file Java phải đáp ứng **toàn bộ** các điều kiện sau:

| Hạng mục            | Yêu cầu                               |
|---------------------|---------------------------------------|
| Encoding            | UTF-8, **không** dùng BOM             |
| End of line         | Unix (LF) — **không** dùng DOS (CRLF) |
| Trailing whitespace | Xóa sạch                              |
| Dòng cuối file      | Luôn có 1 dòng trống                  |

## 3. Quy tắc convention riêng cho dự án

### 3.1. Import

- Tuyệt đối **KHÔNG** dùng **wildcard import**, kể cả trong **test code**.
- **Thứ tự** nhóm import (mỗi nhóm cách nhau 1 dòng trống, trong mỗi nhóm sắp xếp theo alphabet):

  1. `java.*`
  2. *(dòng trống)*
  3. `org.springframework.*`
  4. *(dòng trống)*
  5. Các import của thư viện khác, sắp xếp thứ tự lần lượt theo **cặp**
  6. *(dòng trống)*
  7. Các `import static` khác

### 3.2. Thứ tự các thành phần trong file Java

1. Field `static`
2. Field thông thường
3. Hàm khởi tạo
4. Phương thức (có thể `private`) được gọi từ hàm khởi tạo
5. Static factory method
6. Getter/Setter (nếu định nghĩa thủ công, không dùng Lombok)
7. Phương thức override/implement từ base class/interface
8. Các phương thức khác
9. Phương thức override từ `java.lang.Object` — theo đúng thứ tự: `toString()` → `equals()` → `hashCode()`

> **Lưu ý:** Nếu một `public` API gọi tới các phương thức `private`/`protected`, các phương thức đó phải được đặt **đi theo cặp** (ngay cạnh phương thức gọi chúng), thay vì gom nhóm theo access modifier.

### 3.3. Kế thừa / Implement

- Ưu tiên đặt `extends`/`implements` trên **cùng 1 dòng**. Nếu không đủ chỗ:

  ```java
  class A extends B
          implements C, D, E, F {
  }
  ```

- Class/interface càng quan trọng thì đặt càng **gần** định nghĩa lớp.

### 3.4. Đặt tên constant

- Constant thực sự (`static final` bất biến) → `SNAKE_UPPERCASE`.
- Biến `static final` nhưng **không** phải constant thực sự → đặt tên theo kiểu thuộc tính thường: `thisOne`.
- Biến thông thường: hạn chế dùng tên 1 chữ cái (`x`, `i`, ...) vì gây khó hiểu.

### 3.5. Ternary operator

- Nhánh trả về khi **khác `null`** viết trước: `foo != null ? foo : bar`.
- **Không** được lồng nhiều ternary với nhau.

### 3.6. Null safety

Ưu tiên áp dụng theo thứ tự sau:

1. Dùng `org.springframework.util.Assert.notNull` / `Assert.state` kèm message rõ ràng:

   ```java
   // Với tham số
   public void handle(Event event) {
       Assert.notNull(event, "Event must not be null");
       // ...
   }

   // Với field
   Event event = ...
   Assert.state(event != null, "Event must not be null");
   ```

2. Kết hợp annotation [JSpecify](https://github.com/jspecify/jspecify) với xử lý runtime của [NullAway](https://github.com/uber/NullAway).

### 3.7. `@Contract`

- Dùng annotation `@Contract` (Spring hoặc JetBrains qua `org.jetbrains:annotations`) để mô tả hành vi phương thức dựa trên tham số đầu vào — NullAway sẽ dựa vào đây để xử lý tại runtime.

### 3.8. `@Override`

- Chỉ thêm `@Override` khi lớp con **thực sự override hành vi** đã có ở lớp cha — **không** dùng khi chỉ implement (cung cấp lần đầu) hành vi cho phương thức.

### 3.9. Utility class

Một class được coi là Utility khi thỏa **đồng thời**:

1. Có hậu tố `Utils` trong tên (`StringUtils`, `DateUtils`, ...)
2. Là `final class` hoặc `abstract class`
3. Có 1 constructor mặc định, đánh dấu `private`
4. Toàn bộ phương thức bên trong là `static`

### 3.10. Từ khóa `var`

Chỉ dùng `var` khi:

- Kiểu là **primitive type** đơn giản, hoặc
- Ngữ cảnh đủ rõ ràng qua tên biến/phương thức, ví dụ:
  - `isAvailable` → ngầm hiểu là `boolean/Boolean`
  - `new T()` → ngầm hiểu là kiểu `T`

Ngoài các trường hợp trên, **luôn khai báo kiểu tường minh**.

### 3.11. Javadoc

1. Câu mô tả logic đầu tiên: viết theo **imperative style** (giống các thư viện chuẩn).
2. Luôn để 1 dòng trống trước khi thêm tag `@param`:

   ```java
   /**
    * Cộng 2 số với nhau, trả về kết quả là tổng của chúng.
    *
    * @param num1 Số thứ nhất
    * @param num2 Số thứ hai
    * @return Tổng của 2 tham số trên.
    */
   public int add(int num1, int num2) {
       return num1 + num2;
   }
   ```

3. Mô tả nhiều đoạn → dùng thẻ `<p></p>` cho mỗi đoạn **từ đoạn thứ 2** trở đi.
4. Thứ tự tag:
   - **Type-level:** `@author` → `@param` → `@see` → `@deprecated`
   - **Constructor / method / field:** `@param` → `@return` → `@throws` → `@see` → `@deprecated`
5. Cú pháp thay thế chuẩn của Javadoc:
   - `{@code null}` thay vì `<pre>null</pre>`
   - `{@link A}` / `{@linkplain A}` khi type có thể tham chiếu trực tiếp trong code
   - Nếu type chỉ được nhắc đến **duy nhất 1 lần** và không dùng ở đâu khác trong file → dùng **tên đầy đủ** (fully-qualified name) thay vì import
