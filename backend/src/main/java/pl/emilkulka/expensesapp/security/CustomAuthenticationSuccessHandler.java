package pl.emilkulka.expensesapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> successData = new HashMap<>();
        successData.put("message", "Successfully logged in");
        successData.put("role", role);

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(successData));
    }
}
