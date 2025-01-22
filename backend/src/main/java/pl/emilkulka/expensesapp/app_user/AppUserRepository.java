package pl.emilkulka.expensesapp.app_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByEmail(String email);

    @Transactional(readOnly = true)
    AppUser findByUserName(String username);

    List<AppUser> findAppUserByUserRoleEquals(AppUserRole role);

    boolean existsAppUserByEmail(String email);

    boolean existsAppUserByUserName(String userName);
}
