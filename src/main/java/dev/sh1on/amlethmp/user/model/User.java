package dev.sh1on.amlethmp.user.model;

import dev.sh1on.amlethmp.common.template.model.SoftDeletableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
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
    private boolean isAccountExpired;

    @Column("is_account_locked")
    private boolean isAccountLocked;

    @Column("is_credentials_expired")
    private boolean isCredentialsExpired;

    @Column("created_at")
    @CreatedDate
    @InsertOnlyProperty
    private String createdAt;

    @Column("created_by")
    @CreatedBy
    @InsertOnlyProperty
    private String createdBy;

    @Column("last_updated_at")
    @LastModifiedDate
    private String lastUpdatedAt;

    @Column("last_updated_by")
    @LastModifiedBy
    private String lastUpdatedBy;

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
        return !isDisabled;
    }
}
