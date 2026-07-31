package dev.sh1on.amlethmp.user.dto;

import dev.sh1on.amlethmp.user.model.Role;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@SuppressWarnings("NullAway.Init")
public class UserDto {
    private UUID id;
    private String email;
    private String displayName;
    private Role role;
    private Instant createdAt;
    private String createdBy;
}
