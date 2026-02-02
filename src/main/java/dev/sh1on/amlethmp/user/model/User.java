package dev.sh1on.amlethmp.user.model;

import dev.sh1on.amlethmp.common.template.model.SoftDeletableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.*;
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
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Table("users")
public class User extends SoftDeletableEntity implements UserDetails {
    @Id
    private String id;

    @Column("username")
    private String accountName;

    private String displayName;

    @Column("password")
    private String accountPassword;

    private String role;

    private boolean isAccountExpired;

    private boolean isAccountLocked;

    private boolean isCredentialsExpired;

    @CreatedDate
    private String createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private String lastUpdatedAt;

    @LastModifiedBy
    private String lastUpdatedBy;

    @Version
    @Transient
    private Integer version;

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
        return !isDisabled;
    }
}
