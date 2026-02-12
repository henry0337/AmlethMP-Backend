package dev.sh1on.amlethmp.common.template.controller;

import dev.sh1on.amlethmp.common.annotation.Marker;

/**
 * <b>[Internal, Marker Interface, Controller-only]</b> <br>
 *
 * Giao diện base dành riêng cho tác vụ liên quan tới <b>xác thực (authentication)</b> trong nội bộ backend
 * của ứng dụng <a href="https://github.com/henry0337/AmlethMP">AmlethMP</a>. <br><br>
 *
 * Giao diện này là giao diện dạng marker (đánh dấu), lí do là vì ngoài cách đăng nhập bằng cách dùng email & mật khẩu và JWT
 * ra thì có rất nhiều cách đăng nhập khác như <b>OAuth2</b>, <b>Single Sign-On (SSO)</b>, <b>Passwordless</b>,
 * <b>Multi-Factor Authentication (MFA)</b>, <b>vv.</b> và chỉ với cách định nghĩa 2 phương thức cơ bản là {@code register()}
 * và {@code login()} đôi khi sẽ không hợp với ngữ cảnh xác thực khác, nên định nghĩa là marker interface là hợp lý nhất.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@Marker
public interface AmlethMPAuthController { }
