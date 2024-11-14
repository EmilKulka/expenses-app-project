package pl.emilkulka.expensesapp.expense;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "Expense type cannot be null.")
    private ExpenseType type;
    @Size(min = 1, max = 100, message = "Description must be between 1 and 100 characters.")
    private String description;
    @Min(value = 0, message = "Price must be a positive number.")
    private BigDecimal price;
    @PastOrPresent(message = "The date must be in the past or today. Future dates are not allowed.")
    private LocalDate date;
    @NotNull(message = "Please specify if this expense is marked as important or not.")
    private boolean important;
}
