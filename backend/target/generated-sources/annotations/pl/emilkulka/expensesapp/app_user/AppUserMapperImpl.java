package pl.emilkulka.expensesapp.app_user;

import javax.annotation.processing.Generated;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-15T13:19:27+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
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
