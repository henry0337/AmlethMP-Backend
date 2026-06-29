package dev.sh1on.amlethmp.user.model;

import dev.myrlennia237.contract.UserPrincipal;
import dev.myrlennia237.template.entity.Entity;
import dev.myrlennia237.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table("users")
@SuppressWarnings("java:S2057")
public class User extends Entity implements UserDetails, UserPrincipal {
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
        return true;
    }

    @Override
    public UUID getUserId() {
        return CommonUtils.requireNonNull(super.getId());
    }
}
