package com.myblog7.config;

import com.myblog7.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//spring security configuration class
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//after enable this I can use now @PreAuthorize annotation
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean // we can use @Bean only in @Configuration classes
    PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()//in memory authntication make this url open
//                .antMatchers(HttpMethod.POST,"/api/post").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/post").hasRole("ROLE_ADMIN")// instead of this line @PreAuthorise("hasRole('ROLE_ADMIN')") in controller layer
//                .antMatchers(HttpMethod.POST,"/api/post").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/auth/signup").hasRole("ROLE_SUPER")

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();//httpBasic() for basic authentication to block api, only can accessed by angular team
//        let angular team build the form based authentication
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
//    embedded inMemoryAuthentication
//    @Override
//    @Bean
////    password decoding from user and store it in db
//    protected UserDetailsService userDetailsService(){
//        UserDetails user= User.builder().username("Riya").password(passwordEncoder()
//                .encode("password")).roles("User").build();
//
//        UserDetails admin= User.builder().username("admin").password(passwordEncoder()
//                .encode("admin")).roles("Admin").build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//
//    }
}

