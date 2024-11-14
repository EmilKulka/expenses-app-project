package pl.emilkulka.expensesapp.app_user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.emilkulka.expensesapp.app_user.dto.AppUserDto;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    AppUser toAppUser(AppUserDto appUserDto);
}
