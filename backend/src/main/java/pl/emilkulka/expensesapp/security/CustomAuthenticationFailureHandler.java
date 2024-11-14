package pl.emilkulka.expensesapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create a JSON response with an error message
        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", "Invalid username or password");

        // Send JSON response
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorData));
    }

}
