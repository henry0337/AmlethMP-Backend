---
name: translate
description: Dịch chuỗi văn bản (string, long text) sang các ngôn ngữ khác phù hợp với ngữ cảnh của ứng dụng đang phát triển.
---

# Các bước dịch thuật
1. Đưa nội dung cần dịch vào **DeepL Translate API** (thông qua MCP), bạn sẽ nhận được đầu ra của text đã dịch.
2. Áp dụng theo **Quy trình dịch 3 bước** như sau:
  - **Bước 1**: Dịch thô (Drafting) - Tập trung chuyển tải đúng nội dung thông tin.
  - **Bước 2**: Đọc lại bản dịch mà không nhìn vào văn bản gốc, sau đó hãy tự hỏi: "*Người bản xứ đọc câu này có thấy tự nhiên và hiểu đúng ý không?*" (**Google Translate** rất hay gặp vấn đề này)
  - **Bước 3**: Thử dịch ngược lại về ngôn ngữ gốc xem ý nghĩa/từ ban đầu của nó có bị thay đổi hay mất thông tin quan trọng nào đó hay không.

    Sau khi đã vượt qua cả 3 bước, thực hiện trả về đầu ra tương ứng của văn bản đã dịch.
  