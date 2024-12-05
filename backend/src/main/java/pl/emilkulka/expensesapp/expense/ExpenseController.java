package pl.emilkulka.expensesapp.expense;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/expense")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<String> createExpense(@RequestBody @Valid ExpenseDto expenseDto, Principal principal) {
        expenseService.createExpense(expenseDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable("id") UUID id, @RequestBody @Valid ExpenseDto expenseDto, Principal principal) {
        expenseService.updateExpense(id, expenseDto, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Expense updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") UUID id, Principal principal) {
        expenseService.deleteExpense(id, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
