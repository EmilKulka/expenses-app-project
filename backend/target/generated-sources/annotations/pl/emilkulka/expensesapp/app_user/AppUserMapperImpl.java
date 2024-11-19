package pl.emilkulka.expensesapp.app_user;

import javax.annotation.processing.Generated;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T21:39:42+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
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
