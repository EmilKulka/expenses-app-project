package pl.emilkulka.expensesapp.contact;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    @NotNull(message = "Contact type cannot be null.")
    private ContactType type;
    @Size(min = 3, max = 300, message = "Content must be between 1 and 300 characters.")
    private String content;
    @Size(min=3, max = 100, message = "Subject must be between 1 and 300 characters.")
    private String subject;
}
