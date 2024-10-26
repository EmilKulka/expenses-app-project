package pl.emilkulka.expensesapp.expense.exception;

public class ExpenseDoesNotExistException extends RuntimeException {
    public ExpenseDoesNotExistException(String message) {
        super(message);
    }
}
