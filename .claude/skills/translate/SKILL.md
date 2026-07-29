---
name: translate
description: Dịch và tinh chỉnh chuỗi văn bản (string, long text) sang ngôn ngữ đích. Đảm bảo độ chính xác về ngữ nghĩa và văn phong tự nhiên, phù hợp với ngữ cảnh ứng dụng.
user-invocable: false
---

# Quy trình thực thi
Để hoàn thành yêu cầu dịch thuật, bạn phải tuân thủ nghiêm ngặt luồng công việc sau:

1. **Khởi tạo Bản dịch cơ sở:**
   - Đưa văn bản gốc vào **DeepL Translate API** (thông qua MCP) để lấy bản dịch thô ban đầu.

2. **Tinh chỉnh theo Quy trình 3 bước:**
   Sử dụng bản dịch từ DeepL làm nền tảng, áp dụng các bước đánh giá sau:
   - **Bước 1: Đối chiếu thông tin (Accuracy Check):** So sánh bản dịch thô với văn bản gốc. Đảm bảo toàn bộ nội dung, thuật ngữ chuyên ngành và thông điệp được chuyển tải chính xác, không thừa không thiếu.
   - **Bước 2: Chuẩn hóa văn phong (Native Reading):** Đọc lại bản dịch một cách độc lập (tạm quên văn bản gốc đi). Hãy đóng vai một người bản xứ và tự hỏi: *"Câu này đọc có tự nhiên không? Có bị giống văn dịch máy (Google Translate) không?"*. Tinh chỉnh lại cấu trúc câu và từ vựng cho thật mượt mà.
   - **Bước 3: Kiểm chứng ngược (Back-translation):** Tự dịch ngược bản vừa tinh chỉnh về lại ngôn ngữ gốc trong suy nghĩ. Nếu nhận thấy ý nghĩa cốt lõi bị thay đổi so với ban đầu, hãy quay lại Bước 2 để điều chỉnh.

3. **Trả kết quả:**
   - Sau khi bản dịch đã vượt qua cả 3 bước kiểm duyệt trên, xuất ra kết quả cuối cùng. 
   - Yêu cầu: Trả về trực tiếp đoạn văn bản đã dịch, giữ nguyên các định dạng đặc biệt (biến số, HTML tags, markdown) nếu có trong bản gốc.