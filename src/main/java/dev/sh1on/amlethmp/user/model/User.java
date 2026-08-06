package dev.sh1on.amlethmp.user.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.myrlennia237.contract.UserPrincipal;
import dev.myrlennia237.template.entity.Entity;
import dev.myrlennia237.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <b>[API Entity]</b> <br>
 * Entity chứa các field liên quan tới <b>thông tin người dùng ứng dụng</b>.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Table("users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(doNotUseGetters = true)
@SuppressWarnings("java:S2057")
public class User extends Entity implements UserDetails, UserPrincipal {
    /**
     * Email duy nhất của người dùng hệ thống.
     */
    @Column("email")
    private String email;

    /**
     * Tên hiển thị của người dùng hệ thống, có thể trùng nhau.
     */
    @Column("display_name")
    private String displayName;

    /**
     * Mật khẩu của tài khoản mà người dùng đã đăng ký.
     * <p><b>Ghi chú:</b> Giá trị được lưu vào đây <b>phải</b> là giá trị đã được <b>hash</b>.</p>
     */
    @Column("password")
    @ToString.Exclude
    private String accountPassword;

    /**
     * Vai trò của người dùng trong hệ thống.
     * 
     * @see Role
     */
    @Column("role")
    private String role;

    /**
     * Đánh dấu liệu tài khoản đã đăng ký với hệ thống là <b>đã hết hạn</b>.
     */
    @Column("is_account_expired")
    private boolean expired;

    /**
     * Đánh dấu liệu tài khoản đã đăng ký với hệ thống là <b>đã bị khóa</b>.
     */
    @Column("is_account_locked")
    private boolean locked;

    /**
     * Đánh dấu liệu tài khoản đã đăng ký với hệ thống là <b>thông tin người dùng đã hết hạn</b>.
     */
    @Column("is_credentials_expired")
    private boolean credentialExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return accountPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return !isDisabled();
    }

    @SuppressWarnings("DataFlowIssue")
    public UUID getUserId() {
        UUID id = super.getId();
        CommonUtils.requireNonNull(id);
        return id;
    }
}
