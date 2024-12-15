package pl.emilkulka.expensesapp.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserRole;
import pl.emilkulka.expensesapp.app_user.AppUserService;

import java.security.Principal;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @GetMapping("/app-user/verify")
    public ResponseEntity<?> verifySession(Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            AppUserRole role = appUserService.getUserRole(username);
            return ResponseEntity.ok(
                    Map.of(
                            "role", role,
                            "username", username
                    )
            );
        }
        return ResponseEntity.status(401).build();
    }
}
