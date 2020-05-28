package ru.itis.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.filter.JwtTokenFilter;
import ru.itis.provider.JwtTokenProvider;

@EnableWebSecurity
@Configuration
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

        @Autowired
        AuthenticationManager authenticationManager;

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
                    .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, authenticationManager), UsernamePasswordAuthenticationFilter.class);
        }
    }


    @Configuration
    public static class DefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin().loginPage("/signIn").loginProcessingUrl("/authentication").permitAll()
                    .and()
                    .logout().deleteCookies("JSESSIONID")
                    .and()
                    .rememberMe().key("rememberMe").tokenValiditySeconds(5);//86400
            http.authorizeRequests()
                    .antMatchers("/signUp", "/profile/**").permitAll()
                    .antMatchers("/users").hasAuthority("ADMIN")
                    .antMatchers("/profile").authenticated()
                    .antMatchers("/").authenticated();

            http.csrf();
            super.configure(http);
        }
    }
}
