package com.main.sistema_moedas;

import com.main.sistema_moedas.config.AddEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Lab03sistemamoedasApplication implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab03sistemamoedasApplication.class, args);
    }
}
