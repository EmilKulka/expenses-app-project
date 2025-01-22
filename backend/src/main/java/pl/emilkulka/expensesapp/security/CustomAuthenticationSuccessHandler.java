package pl.emilkulka.expensesapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserMapper;
import pl.emilkulka.expensesapp.app_user.AppUserRepository;
import pl.emilkulka.expensesapp.app_user.dto.AppUserLoginResponseDto;
import pl.emilkulka.expensesapp.common.ApiResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper = AppUserMapper.INSTANCE;

    public CustomAuthenticationSuccessHandler(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        String userName = ((AppUserDetails) principal).getUsername();
        AppUser appUser = appUserRepository.findByUserName(userName);
        AppUserLoginResponseDto appUserLoginResponseDto = appUserMapper.toAppUserLoginResponseDto(appUser);

        ApiResponse<AppUserLoginResponseDto> successData = new ApiResponse<>("success", "Successfully logged in", appUserLoginResponseDto);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(successData));
    }
}
