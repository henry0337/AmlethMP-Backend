package dev.sh1on.amlethmp.user.model;

import dev.sh1on.amlethmp.common.template.model.SoftDeletableEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table("users")
public class User extends SoftDeletableEntity implements UserDetails {
    private String email;

    @Column("display_name")
    private String displayName;

    @Column("password")
    private String accountPassword;

    private String role;

    @Column("is_account_expired")
    private boolean expired;

    @Column("is_account_locked")
    private boolean locked;

    @Column("is_credentials_expired")
    private boolean credExpired;

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
        return !credExpired;
    }

    @Override
    public boolean isEnabled() {
        return !isDisabled;
    }
}
