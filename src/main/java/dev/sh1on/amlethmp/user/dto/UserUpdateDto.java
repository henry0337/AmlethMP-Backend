package dev.sh1on.amlethmp.user.dto;

import dev.sh1on.amlethmp.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Normalized;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Data
public class UserUpdateDto {
    @Email
    private String email;

    @Normalized(form = Normalizer.Form.NFKC)
    private String displayName;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=.{12,}).*$")
    @Length(min = 12, max = 255)
    private String password;

    private Role role;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime lastDisabledAt;
    private String lastDisabledBy;
}
