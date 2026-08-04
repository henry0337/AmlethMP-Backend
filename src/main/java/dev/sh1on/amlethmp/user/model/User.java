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
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(doNotUseGetters = true)
@Table("users")
@SuppressWarnings("java:S2057")
public class User extends Entity implements UserDetails, UserPrincipal {
    private String email;

    @Column("display_name")
    private String displayName;

    @Column("password")
    @ToString.Exclude
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

    public String getPassword() {
        return accountPassword;
    }

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
