package pl.emilkulka.expensesapp.app_user;

import javax.annotation.processing.Generated;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserLoginResponseDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-22T14:59:53+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class AppUserMapperImpl implements AppUserMapper {

    @Override
    public AppUser toAppUser(AppUserDto appUserDto) {
        if ( appUserDto == null ) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setUserName( appUserDto.getUserName() );
        appUser.setEmail( appUserDto.getEmail() );
        appUser.setPassword( appUserDto.getPassword() );

        return appUser;
    }

    @Override
    public AppUserLoginResponseDto toAppUserLoginResponseDto(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        AppUserLoginResponseDto appUserLoginResponseDto = new AppUserLoginResponseDto();

        appUserLoginResponseDto.setId( appUser.getId() );
        appUserLoginResponseDto.setUserName( appUser.getUserName() );
        appUserLoginResponseDto.setEmail( appUser.getEmail() );
        appUserLoginResponseDto.setRole( appUser.getUserRole() );

        return appUserLoginResponseDto;
    }
}
