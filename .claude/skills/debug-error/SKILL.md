---
name: debug-error
description: Phân tích báo lỗi, stack trace hoặc hành vi bất thường; truy vết nguyên nhân gốc rễ và đề xuất phương án khắc phục tối thiểu, an toàn cho bất kỳ ngôn ngữ hay nền tảng nào.
effort: high
---

# Quy trình Chẩn đoán & Sửa lỗi Tổng quát (Generic Debugging Protocol)

Mục tiêu: Tìm đúng **nguyên nhân gốc rễ** và thực hiện **can thiệp tối thiểu (surgical patch)** mà không gây ra tác dụng phụ (regression bugs) cho hệ thống.

---

### 1. Phân tích & Phân vùng Lỗi (Triaging & Parsing)
*   **Phân loại sự cố:** Xác định bản chất của lỗi (Runtime Exception, Panic, Memory Leak, Type Mismatch, Logic Bug, Race Condition, Timeout, API Error...).
*   **Tách lọc độ nhiễu (Noise Reduction):** Phân định ranh giới giữa **Code ứng dụng (Application Code)** và **Thư viện/Hệ điều hành (Dependencies/Frameworks/OS)** trong stack trace. Tập trung vào các điểm kích hoạt thuộc về codebase hiện tại.
*   **Trích xuất ngữ cảnh:** Thu thập trạng thái đầu vào (inputs), biến môi trường, luồng dữ liệu (data flow) hoặc cấu hình hệ thống tại thời điểm phát sinh sự cố.

### 2. Truy vết Nguyên nhân Gốc rễ (Root Cause Analysis - RCA)
*   **Lần ngược luồng thực thi (Backward Tracing):** Đi ngược từ điểm đổ vỡ (failure point) về nơi dữ liệu/trạng thái bắt đầu bị hỏng.
*   **Phân biệt Triệu chứng và Nguyên nhân:**
    *   *Triệu chứng (Symptom):* Nơi chương trình bị ngắt/crash hoặc trả về kết quả sai.
    *   *Nguyên nhân gốc (Root Cause):* Nơi giả định logic bị sai, bất biến (invariant) bị vi phạm, hoặc dữ liệu bị biến đổi không đúng kỳ vọng.
*   **Rà soát các góc khuất (Edge Cases):** Kiểm tra giá trị biên, trạng thái rỗng/chưa khởi tạo (null/nil/undefined/none), xung đột bất đồng bộ/đa tiến trình (concurrency/async), hoặc tràn bộ nhớ/tài nguyên.

### 3. Đề xuất Bản vá Tối thiểu (Surgical Patch)
*   **Nguyên tắc can thiệp tối thiểu:** Chỉ sửa đúng vị trí gây lỗi.
*   **Tẩy chay "Vá ngọn" (No Band-aid fixes):** Tuyệt đối không nuốt lỗi (catch & ignore), không chèn kiểm tra rỗng vô căn cứ để "dập" crash mà không hiểu nguyên nhân tại sao dữ liệu bị rỗng.
*   **Bảo toàn kiến trúc:** Giữ nguyên quy chuẩn thiết kế (coding standards), giao ước hàm (function contracts), và cấu trúc hiện hữu của dự án.

### 4. Xác minh & Phòng ngừa Tái phát (Verification & Prevention)
*   **Giải trình cơ chế:** Giải thích rõ ràng 3 yếu tố: *Hiện tượng lỗi* $\rightarrow$ *Nguyên nhân gốc* $\rightarrow$ *Tại sao bản vá giải quyết được triệt để vấn đề*.
*   **Đánh giá rủi ro kéo theo (Regression Analysis):** Đảm bảo thay đổi không làm đứt gãy các module phụ thuộc hoặc các hàm gọi (callers) liên quan.
*   **Kịch bản kiểm thử (Test Case):** Đề xuất hoặc viết kịch bản test (mô phỏng đúng điều kiện gây lỗi) để đảm bảo lỗi đã được giải quyết dứt điểm và không bị lặp lại trong tương lai.