package pl.emilkulka.expensesapp.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserRepository;


@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {


    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found.", email));
        }
        return new AppUserDetails(appUser);
    }
}
