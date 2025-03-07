package com.project.webapp.drivingschool.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService jwtUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             UserDetailsService jwtUserDetailsService,
                             JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .formLogin()
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/rest/authenticate").permitAll()
                .antMatchers("/rest/activate/student").hasRole(STUDENT)
                .antMatchers("/rest/activate/employee").hasAnyRole(LECTURER, INSTRUCTOR)
                .antMatchers("/rest/activate/admin").hasRole(ADMIN)
                .antMatchers("/rest/employee/byEmail",
                        "/rest/employee/edit",
                        "/rest/employee/edit/password").hasAnyRole(ADMIN, LECTURER, INSTRUCTOR)
                .antMatchers("/rest/employee/all/byRole").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/employee/**").hasRole(ADMIN)
                .antMatchers("/rest/student/all",
                        "/rest/student/edit/full",
                        "/rest/student/email/exist").hasRole(ADMIN)
                .antMatchers("/rest/student/add").permitAll()
                .antMatchers("/rest/student/**").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/course/edit/status",
                        "/rest/report/all").hasRole(ADMIN)
                .antMatchers("/rest/course/**").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/document/add",
                        "/rest/document/delete",
                        "/rest/document/edit/status").hasRole(ADMIN)
                .antMatchers("/rest/document/**").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/payment/add",
                        "/rest/payment/delete",
                        "/rest/payment/edit/status").hasRole(ADMIN)
                .antMatchers("/rest/payment/**").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/theory/all/byEmployee",
                        "/rest/theory/edit/status").hasAnyRole(ADMIN, LECTURER)
                .antMatchers("/rest/theory/**").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/series/all/free").authenticated()
                .antMatchers("/rest/series/**").hasAnyRole(ADMIN, LECTURER)
                .antMatchers("/rest/lecture/all/bySeriesId",
                        "/rest/lecture/hours/passed/bySeriesId").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/lecture/**").hasAnyRole(ADMIN, LECTURER)
                .antMatchers("/rest/driving/all/byEmployee",
                        "/rest/driving/all/actual/byEmployee",
                        "/rest/driving/edit/status").hasAnyRole(ADMIN, INSTRUCTOR)
                .antMatchers("/rest/driving/**").hasAnyRole(ADMIN, STUDENT)
                .antMatchers("/rest/exam/all/byEmployee",
                        "/rest/exam/all/actual/byEmployee",
                        "/rest/exam/edit/**").hasAnyRole(ADMIN, LECTURER, INSTRUCTOR)
                .antMatchers("/rest/exam/**").hasAnyRole(ADMIN, STUDENT)
                .anyRequest().permitAll();

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Role użytkownika, na podstawie których przydzielany jest dostęp:
     */
    private static final String ADMIN = "ADMINISTRATOR";
    private static final String LECTURER = "LECTURER";
    private static final String INSTRUCTOR = "INSTRUCTOR";
    private static final String STUDENT = "STUDENT";

}
