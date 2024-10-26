package pl.emilkulka.expensesapp.app_user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
}
