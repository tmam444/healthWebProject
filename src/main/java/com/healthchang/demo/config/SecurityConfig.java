package com.healthchang.demo.config;

import com.healthchang.demo.config.auth.CustomOAuth2UserService;
import com.healthchang.demo.config.handler.UserLoginFailHandler;
import com.healthchang.demo.domain.MemberAuthority;
import com.healthchang.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final MemberService memberService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole(MemberAuthority.ADMIN.name())
                .antMatchers("/", "/css/**", "/board", "/member", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/search").hasRole(MemberAuthority.USER.name());

        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login");

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureHandler(new UserLoginFailHandler())
                .permitAll();

        http.logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/");

        http.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }
}
