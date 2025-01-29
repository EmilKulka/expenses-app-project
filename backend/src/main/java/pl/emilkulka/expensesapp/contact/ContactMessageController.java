package pl.emilkulka.expensesapp.contact;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.emilkulka.expensesapp.common.ApiResponse;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/contact")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ContactMessageController {

    private final ContactService contactService;
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse<Object>> createContactMessage(@RequestBody ContactDto contactDto, Principal principal) {
        contactService.createContactMessage(contactDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("success", "Successfully created message", null));
    }

    @GetMapping("/unresolved")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<ContactMessage>>> getUnresolvedMessages() {
        List<ContactMessage> messages = contactService.getUnresolvedContactMessages();
        return ResponseEntity.ok(new ApiResponse<>("success", "Unresolved messages retrieved", messages));
    }

    @PostMapping("/{messageId}/reply")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> replyToMessage(
            @PathVariable UUID messageId,
            @RequestBody String replyContent) {
        contactService.replyToContactMessage(messageId, replyContent);
        return ResponseEntity.ok(new ApiResponse<>("success", "Reply added successfully", null));
    }

    @GetMapping("/user-messages")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse<List<ContactMessage>>> getUserMessages(Principal principal) {
        List<ContactMessage> messages = contactService.getUserMessages(principal);
        return ResponseEntity.ok(new ApiResponse<>("success", "User messages retrieved", messages));
    }
}
