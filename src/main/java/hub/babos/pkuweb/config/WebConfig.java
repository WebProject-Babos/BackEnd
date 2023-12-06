package hub.babos.pkuweb.config;


import hub.babos.pkuweb.support.AuthInterceptor;
import hub.babos.pkuweb.support.token.AuthenticationPrincipalArgumentResolver;
import hub.babos.pkuweb.support.token.TokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final TokenManager tokenManager;

    public WebConfig(AuthInterceptor authInterceptor, TokenManager tokenManager) {
        this.authInterceptor = authInterceptor;
        this.tokenManager = tokenManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/signup")
                .excludePathPatterns("/login")
                .excludePathPatterns("/refresh")
                .excludePathPatterns("/posts/all")
                .excludePathPatterns("/posts/{postId}/comments");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .exposedHeaders("Authorization", "Refresh-Token");
    }
    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(tokenManager);
    }
}
