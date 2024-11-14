package pl.emilkulka.expensesapp.http_security;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import pl.emilkulka.expensesapp.expense.Expense;
import pl.emilkulka.expensesapp.http_security.utils.AuthService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AppUserEndpointsTest {

    @Autowired
    private TestRestTemplate http;

    private static final String ADMIN_ENDPOINT = "/api/admin/users";
    private static final String APP_USER_EXPENSES_ENDPOINT = "/api/app-user/expenses";
    private static final String APP_USER_CREATE_EXPENSE_ENDPOINT = "/api/expense";
    private static final String APP_USER_RESET_PASSWORD_ENDPOINT = "/api/app-user/reset-password";
    private static final String EMAIL = "example_user@gmail.com";
    private static final String PASSWORD = "foo";
    private static HttpHeaders headersWithSession;

    @BeforeAll
    static void setUp(@Autowired AuthService authService, @Autowired TestRestTemplate http) throws JSONException {
        authService.REGISTER_APP_USER(createAppUserDTO(), http);
        String sessionCookie = authService.LOGIN_AND_COLLECT_SESSION_COOKIE(EMAIL, PASSWORD, http);

        headersWithSession = new HttpHeaders();
        headersWithSession.add(HttpHeaders.COOKIE, sessionCookie);
    }

    @Test
    void SHOULD_DENY_ACCESS_TO_ADMIN_ENDPOINT_WITH_VALID_APP_USER_SESSION() {
        HttpEntity<?> requestWithSession = new HttpEntity<>(headersWithSession);

        ResponseEntity<String> response = http.exchange(
                ADMIN_ENDPOINT,
                HttpMethod.GET,
                requestWithSession,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void SHOULD_ALLOW_TO_ACCESS_APP_USER_EXPENSES_WITH_VALID_APP_USER_SESSION() {
        HttpEntity<?> requestWithSession = new HttpEntity<>(headersWithSession);

        ResponseEntity<List<Expense>> response = http.exchange(
                APP_USER_EXPENSES_ENDPOINT,
                HttpMethod.GET,
                requestWithSession,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());

    }

    @Test
    void SHOULD_ALLOW_TO_CREATE_NEW_EXPENSE_WITH_VALID_APP_USER_SESSION_AND_VALID_EXPENSE_DTO() throws JSONException {
        JSONObject expenseDto = createExpenseDTO();

        HttpEntity<?> requestWithSession = new HttpEntity<>(expenseDto.toString(), headersWithSession);
        headersWithSession.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = http.exchange(
                APP_USER_CREATE_EXPENSE_ENDPOINT,
                HttpMethod.POST,
                requestWithSession,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("Expense created successfully");
    }

    @Test
    void SHOULD_ALLOW_TO_CHANGE_PASSWORD_WITH_VALID_APP_USER_SESSION_AND_VALID_RESET_PASSWORD_DTO() throws JSONException {
        JSONObject resetPasswordDTO = createResetPasswordDTO();

        HttpEntity<?> requestWithSession = new HttpEntity<>(resetPasswordDTO.toString(), headersWithSession);
        headersWithSession.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = http.exchange(
                APP_USER_RESET_PASSWORD_ENDPOINT,
                HttpMethod.PUT,
                requestWithSession,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Password changed successfully.");
    }


    private static JSONObject createExpenseDTO() throws JSONException {
        JSONObject expenseDto = new JSONObject();
        expenseDto.put("type", "ACCESORIES");
        expenseDto.put("description", "foo");
        expenseDto.put("price", BigDecimal.valueOf(10));
        expenseDto.put("date", LocalDate.now());
        expenseDto.put("important", true);
        return expenseDto;
    }

    private static JSONObject createResetPasswordDTO() throws JSONException {
        JSONObject resetPasswordDTO = new JSONObject();
        resetPasswordDTO.put("email", EMAIL);
        resetPasswordDTO.put("oldPassword", PASSWORD);
        resetPasswordDTO.put("newPassword", "DUMMY_PASSWORD");
        return resetPasswordDTO;
    }

    private static JSONObject createAppUserDTO() throws JSONException {
        JSONObject appUserDto = new JSONObject();
        appUserDto.put("userName", "JohnDoe");
        appUserDto.put("email", EMAIL);
        appUserDto.put("password", PASSWORD);
        return appUserDto;
    }

}
