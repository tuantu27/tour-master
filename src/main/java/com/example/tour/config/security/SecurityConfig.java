package com.example.tour.config.security;

import com.example.tour.config.Login.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
        prePostEnabled = true, jsr250Enabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter   {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // phân quyền
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/css/**","/images/**","/js/**","/vendors/**","/vendors/bootstrap/css/**","/vendors/bootstrap/js/**"
                        , "/vendors/countdown-date-loop-counter/**","/vendors/fontawesome/css/**" ,"/vendors/fontawesome/webfonts/**",
                        "/jquery-ui/images/**","/jquery-ui/**","/vendors/lightbox.dist/**","/vendors/lightbox.dist/css/**",
                        "/vendors/lightbox.dist/js/**","/vendors/masonry/**","/vendors/modal-video/**",
                        "/vendors/slick/**","/vendors/slick/fonts/**", "/admin_style/css/**","/admin_style/js/**","/admin_style/images/**",
                        "/admin_style/webfonts/**","/ckfinder/**","/ckeditor/**").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("Admin")
                .antMatchers("/account/**").hasAnyAuthority("Admin")
                .antMatchers("/subAdmin/**").hasAnyAuthority("SubAdmin")
                // Chỉ cần đăng nhập là có thể vào /...
                .anyRequest().permitAll().and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/login?err=true")
               // .successHandler(loginSuccessHandler)
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().httpBasic()
                .and()
                .exceptionHandling().accessDeniedPage("/login");

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("dinhtu")
                        .password("12345")
                        .roles("Admin")
                        .build());
        return manager;
    }


}
