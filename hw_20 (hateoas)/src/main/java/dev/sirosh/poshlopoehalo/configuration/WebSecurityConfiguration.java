package dev.sirosh.poshlopoehalo.configuration;


import dev.sirosh.poshlopoehalo.security.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Configuration
    @Order(1)
    public static class RestApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        JwtTokenProvider jwtTokenProvider;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable()
                    .csrf().disable();

            http.antMatcher("/api/**").authorizeRequests().anyRequest().permitAll()
                    .and()
                    .apply(new JwtConfigurer(jwtTokenProvider));
        }
    }

    @Configuration
    public static class DefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin().loginPage("/sign_in").loginProcessingUrl("/auth").permitAll();
            http.authorizeRequests()
                    .antMatchers("/", "/sign_up","/sign_in_mail", "/js/**", "/oauth2/**","/movement/**","/signInByMail","/validate/**","/stomp/**","/websocket/**").permitAll()
                    .antMatchers("/profile","/booking/**").authenticated()
                    .antMatchers("/admin", "/new_**","/swagger-ui.html").hasAuthority("ADMIN");
            http.csrf().disable();
            super.configure(http);
        }
    }


}