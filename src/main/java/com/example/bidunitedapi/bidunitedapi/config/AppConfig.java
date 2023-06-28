package com.example.bidunitedapi.bidunitedapi.config;

import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import com.example.bidunitedapi.bidunitedapi.service.UserService;
import com.example.bidunitedapi.bidunitedapi.service.impl.ProductServiceImpl;
import com.example.bidunitedapi.bidunitedapi.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public ProductService productService(){
        return new ProductServiceImpl();
    }
    @Bean
    public UserService userService(){ return new UserServiceImpl();}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }

}
