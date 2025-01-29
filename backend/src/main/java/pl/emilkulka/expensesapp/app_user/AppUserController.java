package pl.emilkulka.expensesapp.app_user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.emilkulka.expensesapp.app_user.dto.AppUserChangePasswordDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.common.ApiResponse;
import pl.emilkulka.expensesapp.expense.Expense;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/app-users")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<AppUser>>> getAppUsers() {
        List<AppUser> appUsers = appUserService.getAllAppUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("success", "Users fetched successfully", appUsers));
    }

    @GetMapping("/expenses")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse<List<Expense>>> getAppUserExpenses(Principal principal) {
        List<Expense> expenses = appUserService.getAppUserExpenses(principal);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("success", "User expenses fetched successfully.", expenses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerAppUser(@RequestBody @Valid AppUserDto appUserDto) {
        appUserService.createAppUser(appUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("success", "User successfully registered.", null));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetAppUserPassword(@RequestBody @Valid AppUserChangePasswordDto appUserChangePasswordDto) {
        appUserService.changePassword(appUserChangePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("success", "Password changed successfully.", null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAppUserById(@PathVariable("id") UUID id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
