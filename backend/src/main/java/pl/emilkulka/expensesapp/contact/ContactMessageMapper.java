package pl.emilkulka.expensesapp.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ContactMessageMapper {
    ContactMessageMapper INSTANCE = Mappers.getMapper(ContactMessageMapper.class);

    @Mapping(source = "content", target = "content")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "subject", target = "subject")
    ContactMessage toContact(ContactDto contactDto);
}
