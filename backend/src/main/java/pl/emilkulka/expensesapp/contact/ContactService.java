package pl.emilkulka.expensesapp.contact;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService {
    private final AppUserRepository appUserRepository;
    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper = ContactMessageMapper.INSTANCE;

    private ContactMessage getContactMessageBy(UUID messageId) {
        return contactMessageRepository.findById(messageId)
                .orElseThrow(() -> new ContactMessageNotFoundException("Contact message with id " + messageId + " not found"));
    }

    public List<ContactMessage> getUnresolvedContactMessages() {
        return contactMessageRepository.findAllByResolvedIsFalse();
    }

    public List<ContactMessage> getUserMessages(Principal principal) {
        AppUser appUser = appUserRepository.findByUserName(principal.getName());
        return contactMessageRepository.findAllBySender(appUser);
    }

    public void createContactMessage(ContactDto contactDto, Principal authorizedUser) {
        AppUser appUser = appUserRepository.findByUserName(authorizedUser.getName());

        ContactMessage contactMessage = contactMessageMapper.toContact(contactDto);
        contactMessage.setSender(appUser);
        contactMessage.setCreatedAt(LocalDateTime.now());
        contactMessage.setResolved(false);

        contactMessageRepository.save(contactMessage);
    }

    @Transactional
    public void replyToContactMessage(UUID messageId, String replyContent) {
        ContactMessage message = getContactMessageBy(messageId);
        message.setReplyContent(replyContent);
        message.setResolved(true);
    }


}

