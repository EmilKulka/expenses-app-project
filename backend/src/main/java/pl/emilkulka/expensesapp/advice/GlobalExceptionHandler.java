package pl.emilkulka.expensesapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.emilkulka.expensesapp.app_user.exception.*;
import pl.emilkulka.expensesapp.contact.ContactMessageNotFoundException;
import pl.emilkulka.expensesapp.expense.exception.ExpenseDoesNotExistException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now(),
                errorMessages,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidCredentialsException.class,
            InvalidPasswordException.class,
            UserDoesNotExistException.class,
            UserWithGivenEmailAlreadyExistsException.class,
            UserWithGivenUserNameAlreadyExistsException.class,
            ExpenseDoesNotExistException.class,
            ContactMessageNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleInvalidCredentialsException(Exception ex) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now(),
                errorMessages,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
