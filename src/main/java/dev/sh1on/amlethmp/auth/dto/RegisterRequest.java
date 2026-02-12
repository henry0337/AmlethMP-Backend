package dev.sh1on.amlethmp.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String displayName;
    private String password;
}
