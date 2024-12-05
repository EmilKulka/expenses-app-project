package pl.emilkulka.expensesapp.app_user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.emilkulka.expensesapp.app_user.dto.AppUserChangePasswordDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.app_user.exception.*;
import pl.emilkulka.expensesapp.expense.Expense;
import pl.emilkulka.expensesapp.expense.ExpenseRepository;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final ExpenseRepository expenseRepository;

    private final PasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper = AppUserMapper.INSTANCE;
    public AppUser getAppUserById(UUID id) {
        return appUserRepository.findById(id)
                .orElseThrow(()-> new UserDoesNotExistException("User with ID: " + id + " not found."));
    }

    public AppUser getAppUserByName(String appUserName) {
        AppUser appUser = appUserRepository.findByUserName(appUserName);
        if(appUser == null) {
            throw new UsernameNotFoundException("User with name: " + appUserName + " not found.");
        }
        return appUser;
    }

    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Transactional
    public void createAppUser(AppUserDto appUserDto) {
        String email = appUserDto.getEmail();
        String userName = appUserDto.getUserName();

        if(appUserRepository.existsAppUserByEmail(email)) {
            throw new UserWithGivenEmailAlreadyExistsException("User with e-mail: " + email + " already exists.");
        }
        if(appUserRepository.existsAppUserByUserName(userName)) {
            throw new UserWithGivenUserNameAlreadyExistsException("User with name: " + userName + " already exists.");
        }

        String encodedPassword = passwordEncoder.encode(appUserDto.getPassword());

        AppUser newAppUser = appUserMapper.toAppUser(appUserDto);
        newAppUser.setPassword(encodedPassword);
        newAppUser.setUserRole(AppUserRole.USER);

        appUserRepository.save(newAppUser);
    }

    @Transactional
    public void deleteAppUser(UUID appUserId) {
        AppUser appUser = getAppUserById(appUserId);

        appUserRepository.delete(appUser);
    }


    @Transactional
    public void changePassword(AppUserChangePasswordDto appUserChangePasswordDto) {
        String dtoEmail = appUserChangePasswordDto.getEmail();
        String dtoOldPassword = appUserChangePasswordDto.getOldPassword();
        String dtoNewPassword = appUserChangePasswordDto.getNewPassword();
        AppUser appUser = appUserRepository.findByEmail(dtoEmail);

        if(appUser.getUserRole().equals(AppUserRole.ADMIN)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if(appUser == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String appUserPassword = appUser.getPassword();

        if(!passwordEncoder.matches(dtoOldPassword, appUserPassword)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if(passwordEncoder.matches(dtoNewPassword, appUserPassword)) {
            throw new InvalidPasswordException("New password cannot be the same as old password.");
        }

        appUser.setPassword(passwordEncoder.encode(dtoNewPassword));
    }

    public List<Expense> getAppUserExpenses(Principal principal) {
        AppUser appUser = getAppUserByName(principal.getName());

        return expenseRepository.findExpensesByUserName(appUser.getUserName());
    }

}
