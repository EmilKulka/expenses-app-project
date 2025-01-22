package pl.emilkulka.expensesapp.app_user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.emilkulka.expensesapp.app_user.dto.AppUserChangePasswordDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.common.ApiResponse;
import pl.emilkulka.expensesapp.expense.Expense;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/app-user")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerAppUser(@RequestBody @Valid AppUserDto appUserDto) {
        appUserService.createAppUser(appUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("success", "User successfully registered.", null));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetAppUserPassword(@RequestBody @Valid AppUserChangePasswordDto appUserChangePasswordDto) {
        appUserService.changePassword(appUserChangePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("success", "Password changed successfully.", null));
    }

    @GetMapping("/expenses")
    public ResponseEntity<ApiResponse<List<Expense>>> getAppUserExpenses(Principal principal) {
        List<Expense> expenses = appUserService.getAppUserExpenses(principal);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("success", "User expenses fetched successfully.", expenses));
    }
}
