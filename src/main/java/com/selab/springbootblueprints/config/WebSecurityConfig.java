package com.selab.springbootblueprints.config;

import com.selab.springbootblueprints.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Setter(onMethod_ = @Autowired)
	private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.authorizeRequests()
        	.antMatchers("/", "/login", "/register", "/accessDenied", "/api/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/**").permitAll()
        	.antMatchers("/**").authenticated()
        .and()
        	.csrf().ignoringAntMatchers("/api/**")
        .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .failureForwardUrl("/loginFail")
            .usernameParameter("username")
        .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/register")
            .invalidateHttpSession(true);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/vendor/**", "/css/**", "/js/**", "/img/**", "/favicon.ico");
    }

}
