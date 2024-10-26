package pl.emilkulka.expensesapp;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserRepository;
import pl.emilkulka.expensesapp.app_user.AppUserRole;

@AllArgsConstructor
@Configuration
public class DBinit implements CommandLineRunner {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        appUserRepository.save(
                new AppUser(
                        "john doe",
                        passwordEncoder.encode("foo"),
                        "john.doe@gmail.com",
                        AppUserRole.ADMIN
                )
        );

    }
}
