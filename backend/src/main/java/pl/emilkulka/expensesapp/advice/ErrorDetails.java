package pl.emilkulka.expensesapp.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Setter
public class ErrorDetails {
    private final String Status;
    private final LocalDateTime timeStamp;
    private final List<String> errorMessages;
    private final int statusCode;

}
