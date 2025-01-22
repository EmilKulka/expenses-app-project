package pl.emilkulka.expensesapp.app_user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.emilkulka.expensesapp.app_user.AppUserRole;

import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserLoginResponseDto {
    private UUID id;
    private String userName;
    private String email;
    private AppUserRole role;
}
