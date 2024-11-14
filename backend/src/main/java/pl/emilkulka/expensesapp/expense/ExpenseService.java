package pl.emilkulka.expensesapp.expense;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserService;
import pl.emilkulka.expensesapp.expense.exception.ExpenseDoesNotExistException;

import java.security.Principal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final AppUserService appUserService;
    private final ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;

    private Expense getExpenseById(UUID expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(()-> new ExpenseDoesNotExistException("Expense with ID: " + expenseId + " not found."));
    }

    public void createExpense(ExpenseDto expenseDto, Principal principal) {
        AppUser appUser = appUserService.getAppUserByName(principal.getName());

        Expense expense = expenseMapper.toExpense(expenseDto);
        expense.setUser(appUser);
        expenseRepository.save(expense);
    }

    @Transactional
    public void updateExpense(UUID expenseId, ExpenseDto expenseDto) {
        Expense expense = getExpenseById(expenseId);

        expense.setDescription(expenseDto.getDescription());
        expense.setType(expenseDto.getType());
        expense.setPrice(expenseDto.getPrice());
        expense.setDate(expenseDto.getDate());
        expense.setImportant(expenseDto.isImportant());
    }

    @Transactional
    public void deleteExpense(UUID expenseId) {
        Expense expense = getExpenseById(expenseId);
        expenseRepository.delete(expense);
    }
}
