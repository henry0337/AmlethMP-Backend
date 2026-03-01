package dev.sh1on.amlethmp.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "")
    @Length(min = 12, max = 255)
    @NotBlank
    private String password;
}
