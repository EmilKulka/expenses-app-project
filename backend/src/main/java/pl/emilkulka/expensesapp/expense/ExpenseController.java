package pl.emilkulka.expensesapp.expense;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.emilkulka.expensesapp.common.ApiResponse;

import java.security.Principal;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/expense")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createExpense(@RequestBody @Valid ExpenseDto expenseDto, Principal principal) {
        expenseService.createExpense(expenseDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("success", "Expense created successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateExpense(@PathVariable("id") UUID id, @RequestBody @Valid ExpenseDto expenseDto, Principal principal) {
        expenseService.updateExpense(id, expenseDto, principal);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("success", "Expense updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") UUID id, Principal principal) {
        expenseService.deleteExpense(id, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
