package pl.emilkulka.expensesapp.app_user;

import javax.annotation.processing.Generated;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-12T13:52:57+0100",
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
}
