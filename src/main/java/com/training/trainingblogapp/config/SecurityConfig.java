package com.training.trainingblogapp.config;

import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/users/**").hasRole("ADMIN")
                .antMatchers("/admin/messages/**").hasAnyRole("ADMIN","MODERATOR")
                .antMatchers("/admin/post/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/post/add-new").hasAnyRole("ADMIN","MODERATOR")
                .antMatchers("/post/edit/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/post/{\\d+}/comment/**").hasAnyRole("ADMIN","MODERATOR")
                .antMatchers("/tags/**").hasAnyRole("ADMIN","MODERATOR")
                .and()
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .loginProcessingUrl("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/index",true)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .clearAuthentication(true);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
