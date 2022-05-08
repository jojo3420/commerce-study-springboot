package com.commerce.shop.config;

import com.commerce.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/member/login/failure")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");


        // routing 별 접근 권할 설정
        http.authorizeRequests()
                // 허용
                .mvcMatchers("/",
                        "/member/**",
                        "/item**"
                        ).permitAll()
                // 어드민만 허용
                .mvcMatchers("/admin/**")
                .hasRole("ADMIN")
                // 이외에 모든 라우팅에 로그인 인증 필요하도록 설정
                .anyRequest().authenticated();

        // 인증 되지 않은 사용자가 리소스에 접근 했을 때 처리할 핸들러 설정
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 1) userDetailService Interface 를 구현하고 있는 구현체 등록
        // 2) 패스워드 암호화 방식 전달
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 리소스 인증 무시 설정
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
