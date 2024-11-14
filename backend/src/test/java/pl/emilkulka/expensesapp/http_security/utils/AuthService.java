package pl.emilkulka.expensesapp.http_security.utils;

import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class AuthService {

    private final static String LOGIN_ENDPOINT = "/login";
    private final static String APP_USER_REGISTER_ENDPOINT = "/api/app-user/register";


    public void REGISTER_APP_USER(JSONObject appUserDto, TestRestTemplate http) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        http.postForEntity(
                APP_USER_REGISTER_ENDPOINT,
                new HttpEntity<>(appUserDto.toString(), headers),
                String.class
        );
    }

    public String LOGIN_AND_COLLECT_SESSION_COOKIE(String userName, String password, TestRestTemplate http) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", userName);
        form.add("password", password);

        ResponseEntity<String> loginResponse = http.postForEntity(
                LOGIN_ENDPOINT,
                new HttpEntity<>(form, headers),
                String.class
        );

        List<String> cookies = loginResponse
                .getHeaders()
                .get(HttpHeaders.SET_COOKIE);

        return cookies.stream()
                .filter(cookie -> cookie.startsWith("JSESSIONID"))
                .findFirst()
                .orElse(null);
    }


}
