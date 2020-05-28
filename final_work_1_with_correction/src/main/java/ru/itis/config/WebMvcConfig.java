package ru.itis.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ru.itis.inspector.UserInspector;

import java.util.Locale;

@EnableWebMvc
@Configuration
class WebMvcConfig {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**")
                        .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }

    @Bean
    MappedInterceptor localeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("locale");
        return new MappedInterceptor(null, localeInterceptor);
    }

    @Bean
    MappedInterceptor userInterceptor() {
        return new MappedInterceptor(null, new UserInspector());
    }

    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:locale");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("en"));
        return sessionLocaleResolver;
    }
}
