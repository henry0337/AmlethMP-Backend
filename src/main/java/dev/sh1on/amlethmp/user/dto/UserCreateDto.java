package dev.sh1on.amlethmp.user.dto;

import dev.sh1on.amlethmp.user.model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCreateDto {
    private String email;
    private String displayName;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private String createdBy;
}
