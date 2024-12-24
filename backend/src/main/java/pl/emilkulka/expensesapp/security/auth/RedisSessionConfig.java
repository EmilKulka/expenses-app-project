package pl.emilkulka.expensesapp.security.auth;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import pl.emilkulka.expensesapp.security.AppUserDetails;
import pl.emilkulka.expensesapp.security.AppUserDetailsMixin;
import pl.emilkulka.expensesapp.security.SimpleGrantedAuthorityMixin;

@Configuration
public class RedisSessionConfig implements BeanClassLoaderAware {

    private ClassLoader loader;

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        mapper.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
        mapper.addMixIn(AppUserDetails.class, AppUserDetailsMixin.class);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
