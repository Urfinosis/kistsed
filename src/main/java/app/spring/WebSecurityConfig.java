package app.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,password,enabled from users where username=?").passwordEncoder(encoder).authoritiesByUsernameQuery(
					"select username, role from users where username=?");
	}	
	@Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
        http
        .authorizeRequests()
            .anyRequest().permitAll()
            .and()
        .formLogin()
            .loginPage("/login").defaultSuccessUrl("/home")
            .failureUrl("/login?error=true")
            .failureHandler(authenticationFailureHandler)
            .permitAll()
            .and()
        .logout()
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth 
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("ADMIN");
    }
    
    @Bean
    public PasswordEncoder encoder() {	
        return new BCryptPasswordEncoder(11);
    }

}