package pl.emilkulka.expensesapp.security;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.emilkulka.expensesapp.security.AppUserDetails;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(as = AppUserDetails.class)
public class AppUserDetailsMixin {
}
