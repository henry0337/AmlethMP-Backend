package dev.sh1on.amlethmp.user.model;

import dev.sh1on.amlethmp.common.shared.model.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@EqualsAndHashCode(callSuper = true) // DANGER: Unknown effect on usage
@Data
@Table("users")
public class User extends AuditingEntity implements UserDetails {
    @Id
    private String id;

    @Column("username")
    private String accountName;

    @Column("password")
    private String accountPassword;

    private String role;

    private boolean isAccountExpired;

    private boolean isAccountLocked;

    private boolean isCredentialsExpired;

    private boolean isEnabled;

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public @Nullable String getPassword() {
        return accountPassword;
    }

    @Override
    public @NonNull String getUsername() {
        return accountName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
