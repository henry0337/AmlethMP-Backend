package dev.sh1on.amlethmp.user.dto;

import dev.sh1on.amlethmp.user.model.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Normalized;
import org.hibernate.validator.constraints.UUID;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Data
public class UserCreateDto {
    @Email
    @NotBlank
    private String email;

    @Normalized(form = Normalizer.Form.NFKC)
    private String displayName;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=.{12,}).*$")
    @Length(min = 12, max = 255)
    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @PastOrPresent
    @NotNull
    private LocalDateTime createdAt;

    @UUID(version = {4, 7}, allowNil = false)
    private String createdBy;
}
