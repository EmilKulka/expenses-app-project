package pl.emilkulka.expensesapp.app_user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.emilkulka.expensesapp.app_user.dto.AppUserChangePasswordDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.app_user.exception.*;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private AppUserRepository appUserRepository;


    @Mock
    private AppUserMapper appUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static AppUser VALID_APP_USER;
    private static AppUser VALID_APP_ADMIN;
    private static AppUserDto VALID_APP_USER_DTO;
    private static AppUserChangePasswordDto VALID_APP_USER_CHANGE_PASSWORD_DTO;
    private final static UUID VALID_APP_USER_ID = UUID.randomUUID();
    private final static UUID VALID_APP_ADMIN_ID = UUID.randomUUID();
    private final static UUID INVALID_APP_USER_ID = UUID.randomUUID();
    private final static String VALID_APP_USER_EMAIL = "john@mail.com";
    private final static String VALID_APP_ADMIN_EMAIL = "foo@mail.com";
    private final static String VALID_PASSWORD = "foo";
    private final static String VALID_NEW_PASSWORD = "bar";
    private static final PasswordEncoder passwordEncoderFoo = new BCryptPasswordEncoder();


    @BeforeAll
    static void setUp() {
        VALID_APP_USER = new AppUser(
                "John Doe",
                passwordEncoderFoo.encode(VALID_PASSWORD),
                VALID_APP_USER_EMAIL,
                AppUserRole.USER
        );
        VALID_APP_USER.setId(VALID_APP_USER_ID);

        VALID_APP_ADMIN = new AppUser(
                "Foo Admin",
                passwordEncoderFoo.encode(VALID_PASSWORD),
                VALID_APP_USER_EMAIL,
                AppUserRole.ADMIN
        );
        VALID_APP_USER.setId(VALID_APP_ADMIN_ID);


        VALID_APP_USER_DTO = new AppUserDto(
                "John Doe",
                VALID_PASSWORD,
                VALID_APP_USER_EMAIL
        );

        VALID_APP_USER_CHANGE_PASSWORD_DTO = new AppUserChangePasswordDto(
                VALID_APP_USER_EMAIL,
                VALID_PASSWORD,
                VALID_NEW_PASSWORD
        );
    }

    @Test
    void SHOULD_RETURN_APP_USER_BY_VALID_ID() {
        // Arrange
        when(appUserRepository.findById(VALID_APP_USER_ID)).thenReturn(Optional.ofNullable(VALID_APP_USER));

        // Act
        AppUser foundUser = appUserService.getAppUserById(VALID_APP_USER_ID);

        // Assert
        assertEquals(foundUser, VALID_APP_USER);
        assertThat(foundUser.getUserName()).isEqualTo(VALID_APP_USER.getUserName());
        assertThat(foundUser.getEmail()).isEqualTo(VALID_APP_USER.getEmail());
        assertThat(foundUser.getUserRole()).isEqualTo(VALID_APP_USER.getUserRole());
        assertThat(foundUser.getId()).isEqualTo(VALID_APP_USER.getId());
        assertThat(foundUser.getPassword()).isEqualTo(VALID_APP_USER.getPassword());

    }

    @Test
    void SHOULD_RETURN_APP_USER_BY_VALID_USER_NAME() {
        // Arrange
        when(appUserRepository.findByUserName("John Doe")).thenReturn(VALID_APP_USER);

        // Act
        AppUser foundUser = appUserService.getAppUserByName("John Doe");

        // Assert
        assertEquals(foundUser, VALID_APP_USER);
        assertThat(foundUser.getUserName()).isEqualTo(VALID_APP_USER.getUserName());
        assertThat(foundUser.getEmail()).isEqualTo(VALID_APP_USER.getEmail());
        assertThat(foundUser.getUserRole()).isEqualTo(VALID_APP_USER.getUserRole());
        assertThat(foundUser.getId()).isEqualTo(VALID_APP_USER.getId());
        assertThat(foundUser.getPassword()).isEqualTo(VALID_APP_USER.getPassword());
    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_TRYING_TO_CREATE_APP_USER_WITH_EXISTING_EMAIL() {
        // Arrange
        when(appUserRepository.existsAppUserByEmail(VALID_APP_USER_EMAIL)).thenReturn(true);

        // Assert
        assertThatThrownBy(() -> appUserService.createAppUser(VALID_APP_USER_DTO))
                .isInstanceOf(UserWithGivenEmailAlreadyExistsException.class)
                .hasMessageContaining("User with e-mail: " + VALID_APP_USER_EMAIL + " already exists.");

    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_TRYING_TO_CREATE_APP_USER_WITH_EXISTING_USERNAME() {
        // Arrange
        when(appUserRepository.existsAppUserByEmail(VALID_APP_USER_EMAIL)).thenReturn(false);
        when(appUserRepository.existsAppUserByUserName(VALID_APP_USER_DTO.getUserName())).thenReturn(true);

        // Assert
        assertThatThrownBy(() -> appUserService.createAppUser(VALID_APP_USER_DTO))
                .isInstanceOf(UserWithGivenUserNameAlreadyExistsException.class)
                .hasMessageContaining("User with name: " + VALID_APP_USER_DTO.getUserName() + " already exists.");

    }

    @Test
    void SHOULD_ALLOW_TO_CHANGE_PASSWORD_WHEN_VALID_DTO() {
        // Arrange
        when(appUserRepository.findByEmail(VALID_APP_USER_EMAIL))
                .thenReturn(VALID_APP_USER);
        when(passwordEncoder.matches(VALID_APP_USER_CHANGE_PASSWORD_DTO.getOldPassword(), VALID_APP_USER.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.matches(VALID_APP_USER_CHANGE_PASSWORD_DTO.getNewPassword(), VALID_APP_USER.getPassword()))
                .thenReturn(false);

        String encodedNewPassword = passwordEncoderFoo.encode(VALID_NEW_PASSWORD);
        when(passwordEncoder.encode(VALID_APP_USER_CHANGE_PASSWORD_DTO.getNewPassword())).thenReturn(encodedNewPassword);

        // Act
        appUserService.changePassword(VALID_APP_USER_CHANGE_PASSWORD_DTO);

        // Assert
        assertThat(VALID_APP_USER.getPassword()).isEqualTo(encodedNewPassword);
    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_TRYING_TO_CHANGE_PASSWORD_WITH_INVALID_EMAIL() {
        // Arrange
        when(appUserRepository.findByEmail(VALID_APP_USER_EMAIL))
                .thenReturn(null);

        // Assert
        assertThatThrownBy(() -> appUserService.changePassword(VALID_APP_USER_CHANGE_PASSWORD_DTO))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining("Invalid email or password");

    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_TRYING_TO_CHANGE_PASSWORD_WITH_INVALID_OLD_PASSWORD() {
        // Arrange
        when(appUserRepository.findByEmail(VALID_APP_USER_EMAIL))
                .thenReturn(VALID_APP_USER);
        when(passwordEncoder.matches(VALID_APP_USER_CHANGE_PASSWORD_DTO.getOldPassword(), VALID_APP_USER.getPassword()))
                .thenReturn(false);

        // Assert
        assertThatThrownBy(() -> appUserService.changePassword(VALID_APP_USER_CHANGE_PASSWORD_DTO))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining("Invalid email or password");

    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_TRYING_TO_CHANGE_PASSWORD_WITH_INVALID_NEW_PASSWORD() {
        //Arrange
        when(appUserRepository.findByEmail(VALID_APP_USER_EMAIL))
                .thenReturn(VALID_APP_USER);
        when(passwordEncoder.matches(VALID_APP_USER_CHANGE_PASSWORD_DTO.getOldPassword(), VALID_APP_USER.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.matches(VALID_APP_USER_CHANGE_PASSWORD_DTO.getNewPassword(), VALID_APP_USER.getPassword()))
                .thenReturn(true);

        // Assert
        assertThatThrownBy(() -> appUserService.changePassword(VALID_APP_USER_CHANGE_PASSWORD_DTO))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("New password cannot be the same as old password.");
    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_TRYING_TO_CHANGE_ADMINS_PASSWORD() {
        when(appUserRepository.findByEmail(VALID_APP_ADMIN_EMAIL))
                .thenReturn(VALID_APP_ADMIN);


    }

    @Test
    void SHOULD_THROW_EXCEPTION_WHEN_INVALID_ID_IS_PASSED_TO_METHOD() {
        // Arrange
        when(appUserRepository.findById(INVALID_APP_USER_ID)).thenThrow(new UserDoesNotExistException("User with ID: " + INVALID_APP_USER_ID + " not found."));

        // Assert
        assertThatThrownBy(() -> appUserService.getAppUserById(INVALID_APP_USER_ID))
                .isInstanceOf(UserDoesNotExistException.class)
                .hasMessageContaining("User with ID: " + INVALID_APP_USER_ID + " not found.");

        assertThatThrownBy(() -> appUserService.deleteAppUser(INVALID_APP_USER_ID))
                .isInstanceOf(UserDoesNotExistException.class)
                .hasMessageContaining("User with ID: " + INVALID_APP_USER_ID + " not found.");
    }


}
