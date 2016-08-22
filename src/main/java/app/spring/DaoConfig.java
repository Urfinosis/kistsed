package app.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("app.dao")
@EnableJpaRepositories(basePackages = "app.dao",entityManagerFactoryRef="emf")
public class DaoConfig {

}
