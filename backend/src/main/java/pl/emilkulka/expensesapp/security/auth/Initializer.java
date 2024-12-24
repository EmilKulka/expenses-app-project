package pl.emilkulka.expensesapp.security.auth;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer extends AbstractHttpSessionApplicationInitializer {
    public Initializer() {
        super(Config.class);
    }
}
