//package com.kokotripadmin.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.csrf.CsrfFilter;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.jdbcAuthentication()
//            .dataSource(dataSource)
//            .passwordEncoder(passwordEncoder())
//            .usersByUsernameQuery("select username, password, enabled " +
//                                  "from admin where username = ?")
//            .authoritiesByUsernameQuery("select u.username, a.name as authority " +
//                                        "from admin u " +
//                                        "left join admin_authority_rel uar on u.id = uar.admin_id " +
//                                        "left join authority a on uar.authority_id = a.id " +
//                                        "where u.username = ?");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        http.addFilterBefore(filter, CsrfFilter.class);
//
//
//        http.authorizeRequests()
//            .antMatchers("/").hasAnyRole("root", "admin")
//            .antMatchers("/resources/css/**",
//                         "/resources/image/**",
//                         "/resources/js/**").permitAll()
//            .anyRequest().authenticated()
//            .and().formLogin()
//            .loginPage("/login").permitAll()
//            .loginProcessingUrl("/authenticateTheUser").permitAll()
//            .and().logout().permitAll()
//            .and().exceptionHandling().accessDeniedPage("/access-denied");
//    }
//}
//
