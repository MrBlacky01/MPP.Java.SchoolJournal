package school.journal.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import school.journal.interceptor.AuthInterceptor;
import school.journal.interceptor.CorsInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan("school.journal")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public AuthInterceptor createAuthInterceptor() {
        return new AuthInterceptor();
    }

//    @Bean
//    public CorsInterceptor createCorsInterceptor() {
//        return new CorsInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createAuthInterceptor()).excludePathPatterns("/api/auth/login");
//        registry.addInterceptor(createCorsInterceptor());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("PUT", "DELETE", "HEAD", "OPTIONS", "GET", "POST");
    }

}