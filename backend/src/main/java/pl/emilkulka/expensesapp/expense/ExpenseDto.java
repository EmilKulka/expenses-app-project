package pl.emilkulka.expensesapp.expense;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    @NotBlank(message = "Expense type cannot be blank.")
    private ExpenseType type;
    @Size(min = 1, max = 100, message = "Description must be between 1 and 100 characters.")
    private String description;
    @Min(value = 0, message = "Price must be a positive number.")
    private BigDecimal price;
    @PastOrPresent(message = "The date must be in the past or today. Future dates are not allowed.")
    private LocalDate date;
    @NotBlank(message = "Please specify if this expense is marked as important or not.")
    private boolean important;
}
