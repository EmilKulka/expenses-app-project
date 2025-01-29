package pl.emilkulka.expensesapp.contact;

public class ContactMessageNotFoundException extends RuntimeException{
    public ContactMessageNotFoundException(String message) {
        super(message);
    }
}
