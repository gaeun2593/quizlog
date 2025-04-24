package com.mtvs.quizlog.global.config;

import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.global.config.handler.AuthFailHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthFailHandler authFailHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // html에서 patch 요청 보낼 수 있도록 빈 등록
    // 원래는 자동 등록인데 필터체인 커스텀하면서 수동으로 등록해줘야 함
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (테스트 용도)
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/user/sign-up", "/auth/login", "/","/*").permitAll();
                auth.requestMatchers("/user/my-page").hasAnyAuthority(Role.STUDENT.getRole(), Role.TEACHER.getRole(), Role.ADMIN.getRole());
                auth.anyRequest().authenticated();
                }).formLogin(login -> {
                    login.loginPage("/auth/login");
                    login.usernameParameter("email");
                    login.passwordParameter("password");
                    login.defaultSuccessUrl("/main", true);
                    login.failureUrl("/auth/login");
                    login.failureHandler(authFailHandler);
                }).logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                    logout.logoutSuccessUrl("/");
                }).sessionManagement(session -> {
                    session.maximumSessions(1);
                    session.invalidSessionUrl("/");
                });

        return http.build();
    }
}