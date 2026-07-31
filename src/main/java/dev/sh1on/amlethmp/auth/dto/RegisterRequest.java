package dev.sh1on.amlethmp.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Normalized;

import java.text.Normalizer;

@Data
@SuppressWarnings("NullAway.Init")
public class RegisterRequest {
    @Email
    @NotBlank
    private String email;

    @Normalized(form = Normalizer.Form.NFKC)
    private String displayName;

    @Pattern(regexp = "^(?=.*\\p{Lu})(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=.{12,}).*$")
    @Length(min = 12, max = 255)
    @NotBlank
    private String password;
}
