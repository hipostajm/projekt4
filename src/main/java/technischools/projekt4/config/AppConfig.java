package technischools.projekt4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // singleton scope
    @Bean
    public AuthorStatsHolder authorStatsHolder() {
        return new AuthorStatsHolder();
    }
}
