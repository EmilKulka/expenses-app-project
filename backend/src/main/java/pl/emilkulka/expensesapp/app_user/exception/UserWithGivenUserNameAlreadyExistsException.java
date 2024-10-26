package pl.emilkulka.expensesapp.app_user.exception;

public class UserWithGivenUserNameAlreadyExistsException extends RuntimeException {
    public UserWithGivenUserNameAlreadyExistsException(String message) {
        super(message);
    }
}
