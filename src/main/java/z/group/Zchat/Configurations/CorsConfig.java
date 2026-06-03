package z.group.Zchat.Configurations;

import jdk.jfr.Category;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://zchat-front.vercel.app")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
