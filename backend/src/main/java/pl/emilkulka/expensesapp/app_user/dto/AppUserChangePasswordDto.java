package pl.emilkulka.expensesapp.app_user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppUserChangePasswordDto {
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String oldPassword;
    @NotBlank
    private final String newPassword;
}
