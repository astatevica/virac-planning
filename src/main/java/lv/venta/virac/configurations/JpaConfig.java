package lv.venta.virac.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lv.venta.auditing.ApplicationAuditAware;

@Configuration
@EnableJpaAuditing
@Profile("!test")
public class JpaConfig {

    @Bean
    public AuditorAware<Integer> applicationAuditAware() {
        return new ApplicationAuditAware();
    }
}
