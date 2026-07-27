# Phong cách code

- Luôn tuân thủ coding convention được định nghĩa trong file PDF sau của Oracle: [Java Code Conventions](https://www.oracle.com/a/tech/docs/java/codeconventions.pdf) và lấy nó làm khung mẫu tiêu chuẩn trừ khi có yêu cầu chỉnh sửa convention khác từ **người sở hữu repo (tôi - Myrlennia)**.
- Source file Java tiêu chuẩn phải đáp ứng được toàn bộ các điều kiện sau:
  + Luôn được mã hóa bằng chuẩn **UTF-8 không sử dụng BOM**.
  + **End of Line** luôn là **Unix (LF)**, không sử dụng **DOS (CRLF)**.
  + Luôn xóa hết **trailing whitespace** có trong file.
  + File luôn phải có 1 **dòng trống** cuối cùng.

- Java coding convention (dành riêng cho dự án này)
  + Không sử dụng **wildcard import** bằng **bất cứ giá nào**, kể cả trong **test code**

  + Thứ tự import luôn là: 
    1. **Các API chuẩn của Java (`java.*`)**
    2. Một dòng trống
    3. **Các API của framework Spring (`org.springframework.*`)** 
    4. Một dòng trống 
    5. Các `import static` khác
    
  (Hiển nhiên phải sắp xếp theo thứ tự bảng chữ cái theo thứ tự trên.)

  + Thứ tự sắp xếp của các element trong một file Java:
    1. Các field `static`
    2. Các field thông thường
    3. Hàm khởi tạo 
    4. Các **phương thức (có thể private)** được gọi từ **hàm khởi tạo**
    5. Các phương thức dạng factory (hay được gọi là **static factory method**)
    6. Getter/Setter (nếu không dùng **Lombok** và định nghĩa thủ công)
    7. Các phương thức được **override/implement** từ một **base class/interface**
    8. Các phương thức khác
    9. Phương thức được ghi đè từ class `java.lang.Object`, bao gồm (và theo thứ tự) `toString()`, `equals()` và `hashCode()` nếu cần phải được cung cấp riêng.

    + Nếu có các phương thức `private` và `protected` được gọi trong một `public` API nào đó, đảm bảo chúng luôn đi theo cặp thay vì nhóm theo từng access modifier.

  + Luôn cố gắng đặt các class được `extends`/`implements` trên cùng 1 dòng, nếu không thể thì ghi theo dạng:
    ```java
    class A extends B 
            implements C, D, E, F {
    }
    ```
    và luôn nhớ lớp nào càng quan trọng thì phải đặt càng gần với định nghĩa

  + Một **constant** phải được đặt tên theo đúng convention của Java: `SNAKE_UPPERCASE`, và phải là một biến dạng `static final`, trong trường hợp biến đó được định nghĩa là `static final` nhưng nó **không thực sự** là một constant có thể sử dụng cách viết tiêu chuẩn của một thuộc tính thông thường: `thisOne`; với **biến thông thường**, **hạn chế** sử dụng các **chữ cái** đơn giản để đặt tên (`x`, `i`, vv.) vì nó sẽ gây khó hiểu cho người đọc.

  + Với **ternary operator**, luôn đảm bảo rằng điều kiện trả về khi **KHÔNG** `null` sẽ được viết ra trước (`foo != null ? foo : bar`) và **KHÔNG** bao giờ được lồng loại toán tử trên với nhau, vì nó sẽ đủ để gây rối code nếu như không được tổ chức đúng cách.

  + Với tính năng **null safety**, ưu tiên áp dụng những cách sau để xử lý giá trị `null`:
    1. Ưu tiên sử dụng `org.springframework.util.Assert.notNull` hoặc `org.springframework.util.Assert.state` để lần lượt kiểm tra biến hoặc tham số có chứa giá trị `null` hay không và trả về message tương ứng:
        ```java
        // Áp dụng với tham số
        public void handle(Event event) {
            Assert.notNull(event, "Event must not be null");
            //...
        }

        // Hoặc áp dụng với field
        //...
        Event event = ...
        Assert.state(event != null, "Event must not be null");
        //...
        ```
    2. Kết hợp giữa các annotation của [JSpecify](https://github.com/jspecify/jspecify) và khả năng xử lý `null` tại runtime của [NullAway](https://github.com/uber/NullAway).
  
  + Annotation `@Contract` của Spring (hoặc của JetBrains, thông qua `org.jetbrains:annotations`) sẽ giúp định nghĩa hành vi của phương thức dựa trên nhưng tham số hiện tại của nó và đồng thời cũng sẽ được [NullAway](https://github.com/uber/NullAway) dựa vào và cung cấp hành vi tương ứng tại runtime

  + Chỉ điền annotation `@Override` lên một phương thức khi một lớp **THỰC SỰ override hành vi** của phương thức nằm trong lớp cha, chứ không phải là khi **implement hành vi** cho các phương thức đó.

  + Một lớp được coi là **Utility** khi và chỉ khi thỏa mãn **đồng thời** các điều kiện sau:
    1. Phải có hậu tố **Utils** trong trên lớp (`StringUtils`, `DateUtils`, vv.)
    2. Phải được đánh dấu là `final class` hoặc `abstract class`.
    3. Phải có 1 hàm khởi tạo mặc định được đánh dấu là `private`.
    4. Các hàm bên trong bắt buộc phải được đánh dấu là `static`.

  + Từ khóa `var` chỉ được phép sử dụng khi **định nghĩa** một **biến đơn giản** (thông qua **primitive type**) hoặc phải **đủ rõ ràng về ngữ cảnh** khi đọc tên của trường/phương thức:
    + `isAvailable` tức ám chỉ kiểu `boolean/Boolean` (do bắt đầu bằng từ **is**).
    + `new T()` tức chỉ ám chỉ kiểu `T`.

    Ngoài trường hợp trên ra, phải luôn luôn định nghĩa kiểu rõ ràng mỗi khi sử dụng.

  + Với **Javadoc**, luôn tuân theo phong cách viết sau:
    1. Với câu đầu tiên mô tả logic của phương thức, LUÔN sử dụng phong cách viết của **câu mệnh lệnh (imperative style)**, giống như cách mà bất cứ thư viện nào viết.
    2. Khi viết xong phần mô tả logic của phương thức, nếu có tham số của hàm được định nghĩa, hãy đảm bảo LUÔN cách 1 dòng ra trước khi thêm tag `@param`:

        ```java
        /**
         * Cộng 2 số với nhau, trả về kết quả là tổng của chúng.
         *                         // <--- Luôn có dòng trống ở đây!
         * @param num1 Số thứ nhất
         * @param num2 Số thứ hai
         * @return Tổng của 2 tham số trên.
         */
        public int add(int num1, int num2) {
            return num1 + num2;
        }
        ```
    3. Nếu như mô tả có nhiều đoạn, ưu tiên sử dụng cặp thẻ `<p></p>` của HTML cho từng đoạn, bắt đầu từ đoạn thứ 2 trở đi.
    4. **Thứ tự xuất hiện** của các tag trong Javadoc dành cho **type-level**: `@author` -> `@param` -> `@see` -> `@deprecated`, còn dành cho các **đối tượng còn lại** (bao gồm hàm khởi tạo, phương thức, field) sẽ là `@param` -> `@return` -> `@throws` -> `@see` -> `@deprecated`.
    5. Luôn sử dụng cú pháp riêng của Javadoc cho các phần có thể thay thế:
      - `{@code null}` thay vì là `<pre>null</pre>`
      - `{@link A}` hoặc `{@linkplain A}` nếu chúng có thể được tham chiếu trực tiếp trong code.
      - Nếu một type được tham chiếu **duy nhất 1 lần** và nó **không còn** được sử dụng ở đâu đó khác **trong cùng** 1 file đó, ưu tiên sử dụng **tên đầy đủ** của chúng thay vì thực hiện **import**.

