package com.example.lunitexam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
//                .allowedOrigins("http://localhost:3000")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
