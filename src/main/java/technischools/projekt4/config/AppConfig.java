package technischools.projekt4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class AppConfig {

    // request scope
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestScopedAuditLog auditLog() {
        return new RequestScopedAuditLog();
    }

    // singleton scope
    @Bean
    public AuthorStatsHolder authorStatsHolder() {
        return new AuthorStatsHolder();
    }
}
