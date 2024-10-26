package pl.emilkulka.expensesapp.app_user.exception;

public class UserWithGivenEmailAlreadyExistsException extends RuntimeException {
    public UserWithGivenEmailAlreadyExistsException(String message) {
        super(message);
    }
}
