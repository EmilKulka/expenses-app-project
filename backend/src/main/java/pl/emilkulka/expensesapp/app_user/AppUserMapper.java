package pl.emilkulka.expensesapp.app_user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;
import pl.emilkulka.expensesapp.app_user.dto.AppUserLoginResponseDto;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    AppUser toAppUser(AppUserDto appUserDto);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "userRole", target = "role")
    AppUserLoginResponseDto toAppUserLoginResponseDto(AppUser appUser);
}
