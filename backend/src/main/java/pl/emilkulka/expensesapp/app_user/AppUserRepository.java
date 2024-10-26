package pl.emilkulka.expensesapp.app_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.emilkulka.expensesapp.app_user.AppUser;

import java.util.UUID;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByEmail(String email);

    AppUser findByUserName(String username);

    boolean existsAppUserByEmail(String email);

    boolean existsAppUserByUserName(String userName);
}
