package pl.emilkulka.expensesapp.http_security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.http_security.utils.AuthService;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdminEndpointsTest {

    @Autowired
    TestRestTemplate http;
    private static final String ADMIN_ENDPOINT = "/api/admin/users";
    private static final String APP_USER_CREATE_EXPENSE_ENDPOINT = "/api/expense";
    private static final String VALID_ADMIN_EMAIL = "john.doe@gmail.com";
    private static final String VALID_ADMIN_PASSWORD = "foo";

    private static HttpHeaders headersWithSession;

    @BeforeAll
    static void setUp(@Autowired AuthService authService, @Autowired TestRestTemplate http) {
        String sessionCookie = authService.LOGIN_AND_COLLECT_SESSION_COOKIE(VALID_ADMIN_EMAIL, VALID_ADMIN_PASSWORD, http);
        headersWithSession = new HttpHeaders();
        headersWithSession.add(HttpHeaders.COOKIE, sessionCookie);
    }



    @Test
    void SHOULD_ALLOW_TO_ACCESS_ADMIN_ENDPOINT_WITH_VALID_ADMIN_SESSION() {
        HttpEntity<?> requestWithSession = new HttpEntity<>(headersWithSession);

        ResponseEntity<List<AppUser>> response = http.exchange(
                ADMIN_ENDPOINT,
                HttpMethod.GET,
                requestWithSession,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void SHOULD_DENY_ACCESS_TO_USER_ENDPOINT_WITH_VALID_ADMIN_SESSION() {
        HttpEntity<?> requestWithSession = new HttpEntity<>(headersWithSession);

        ResponseEntity<String> response = http.exchange(
                APP_USER_CREATE_EXPENSE_ENDPOINT,
                HttpMethod.GET,
                requestWithSession,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
