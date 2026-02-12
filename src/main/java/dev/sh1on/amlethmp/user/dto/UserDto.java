package dev.sh1on.amlethmp.user.dto;

import dev.sh1on.amlethmp.user.model.Role;

import java.time.LocalDateTime;

public record UserDto(String email, String displayName, Role role, LocalDateTime createdAt, String createdBy) { }
