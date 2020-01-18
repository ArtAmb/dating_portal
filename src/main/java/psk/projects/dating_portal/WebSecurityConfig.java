package psk.projects.dating_portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/", "/register", "/am/i/authorized").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login")
                .usernameParameter("login")
                .passwordParameter("password")

                .failureHandler(new SimpleUrlAuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse response,
                                                AuthenticationException arg2) throws IOException, ServletException {
//                arg1.sendRedirect("/view/sign-in-error");
                System.out.println(arg2);
                System.out.println("Authentication Failure");
                response.sendError(HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase());

            }
        }).successHandler(new SimpleUrlAuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1,
                                                Authentication arg2) throws IOException, ServletException {
                System.out.println("Authentication Success");

            }
        }).permitAll()
                .and().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest arg0, HttpServletResponse arg1,
                                 AuthenticationException arg2) throws IOException, ServletException {
                arg1.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("Authentication Entry Point");

            }
        }).and().logout().permitAll().logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                System.out.println("logout success");
            }
        })
                .and().headers().frameOptions().disable().and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT login, password, active FROM app_user WHERE login = ?")
                .authoritiesByUsernameQuery("SELECT u.login, r.name rola " +
                        "from app_user u " +
                        "inner join app_user_roles on u.id = app_user_roles.app_user_id " +
                        "inner join role r on r.id = app_user_roles.roles_id " +
                        "where u.login= ?")
                .passwordEncoder(new BCryptPasswordEncoder());

    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}