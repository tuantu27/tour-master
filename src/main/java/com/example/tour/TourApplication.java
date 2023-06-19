package com.example.tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class TourApplication implements WebMvcConfigurer{
//    @Autowired
//    SecurityInterceptor securityInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(TourApplication.class, args);
    }
//    @Override
//  //  public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(securityInterceptor);
//    }

}

