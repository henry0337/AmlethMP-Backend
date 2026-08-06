package dev.sh1on.amlethmp.user.dto;

import java.time.Instant;
import java.util.UUID;

import dev.sh1on.amlethmp.user.model.Role;
import lombok.Data;

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
