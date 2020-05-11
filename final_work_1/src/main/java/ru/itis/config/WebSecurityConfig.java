package ru.itis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().loginPage("/signIn").loginProcessingUrl("/authentication").permitAll();
        http.authorizeRequests()
                .antMatchers("/signUp").permitAll()
                .antMatchers("/users").hasAuthority("ADMIN")
                .antMatchers("/profile").authenticated()
                .antMatchers("/").authenticated();

        http.csrf().disable();
        super.configure(http);
    }

}
