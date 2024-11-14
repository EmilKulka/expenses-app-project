package pl.emilkulka.expensesapp.app_user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.emilkulka.expensesapp.app_user.dto.AppUserChangePasswordDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.expense.Expense;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/app-user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAppUser(@RequestBody @Valid AppUserDto appUserDto) {
        appUserService.createAppUser(appUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered.");
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetAppUserPassword(@RequestBody @Valid AppUserChangePasswordDto appUserChangePasswordDto) {
        appUserService.changePassword(appUserChangePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully.");
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAppUserExpenses(Principal principal) {
        List<Expense> expenses = appUserService.getAppUserExpenses(principal);
        return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }
}
