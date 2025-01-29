package pl.emilkulka.expensesapp.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.emilkulka.expensesapp.app_user.AppUser;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, UUID> {
    List<ContactMessage> findAllByResolvedIsFalse();
    List<ContactMessage> findAllBySender(AppUser sender);
}
