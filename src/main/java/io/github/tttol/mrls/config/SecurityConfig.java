package io.github.tttol.mrls.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
//                .formLogin(login -> login
//                                .loginProcessingUrl("/login")
////                        .loginPage("/login") //カスタムのログインページを指定する場合
//                                .defaultSuccessUrl("/list")
//                                .failureUrl("/login?error")
//                                .permitAll()
//                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .authorizeHttpRequests(authz -> authz
                        // css/**などのstaticなファイルは認証不要
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // 認証不要
                        .requestMatchers("/").permitAll()
                        // 要認証
                        .anyRequest().authenticated()
                )
                .oauth2Login();
        return http.build();
    }

//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
}