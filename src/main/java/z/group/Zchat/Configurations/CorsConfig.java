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
                .allowedOrigins("https://zchat-front-bj9ijedjf-charan0g0hs-projects.vercel.app")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
