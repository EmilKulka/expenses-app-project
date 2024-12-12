package pl.emilkulka.expensesapp.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserService;

import java.security.Principal;
import java.util.Map;

@RestController
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/app-user/verify")
    public ResponseEntity<?> verifySession(Principal principal) {
        if (principal != null) {
            AppUser appUser = appUserService.getAppUserByName(principal.getName());
            return ResponseEntity.ok(
                    Map.of(
                            "role", appUser.getUserRole(),
                            "username", appUser.getUserName()
                    )
            );
        }
        return ResponseEntity.status(401).build();
    }
}
