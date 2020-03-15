package kz.attractorschool.microgram.securingweb;

import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserRepo userRepo;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails userDetail =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("p")
                        .roles("USER")
                        .build();

        List<UserDetails> userDetails = new LinkedList<>();
        for(kz.attractorschool.microgram.model.User user: kz.attractorschool.microgram.model.User.getUsers()){
            userDetail =
                    User.withDefaultPasswordEncoder()
                            .username(user.getAccountName())
                            .password(user.getPassword())
                            .roles("USER")
                            .build();
            userDetails.add(userDetail);
        }
        UserDetails finalUserDetail = userDetail;
        kz.attractorschool.microgram.model.User.getUsers()
                .stream()
                .filter(user -> user.getAccountName().equals(finalUserDetail.getUsername()))
                .forEach(user -> user.setLoggedIn(finalUserDetail.isEnabled()));

        return new InMemoryUserDetailsManager(userDetail);
    }
}
