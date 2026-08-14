package dev.sh1on.amlethmp.user.dto;

import java.text.Normalizer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Normalized;

import dev.sh1on.amlethmp.user.enums.Role;

@Data
@SuppressWarnings("NullAway.Init")
public class UserUpdateDto {
    @Email
    private String email;

    @Normalized(form = Normalizer.Form.NFKC)
    private String displayName;

    @Pattern(regexp = "^(?=.*\\p{Lu})(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=.{12,}).*$")
    @Length(min = 12, max = 255)
    private String password;

    private Role role;
}
