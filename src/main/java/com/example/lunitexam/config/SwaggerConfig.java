package com.example.lunitexam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lunit Exam 과제 API")
                        .description("이미지 파일을 upload 후 분석을 진행한다.")
                        .version("1.0.0"));
    }
}