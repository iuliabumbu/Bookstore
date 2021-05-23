package ro.sd.a2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ro.sd.a2.service.AdminService;
import ro.sd.a2.service.UserService;

@Configuration
//@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Configuration
    @Order(1)
    public class ApplicationSecurityConfigAdmin extends WebSecurityConfigurerAdapter {

        @Autowired
        private AdminService adminDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/indexAdmin", "/addBook", "/addGenre", "/addPromotions",
                            "/addShipper", "/addUser", "/changeStatus", "/errorAdmin", "/successAdmin",
                            "/updateBook", "/updateGenre", "/updateShipper", "/updateUser",
                            "/viewBook", "/viewGenre", "/viewShipper", "/viewUser", "/viewOrder").hasAuthority("admin")
                    .and().formLogin().loginPage("/adminLogin").loginProcessingUrl("/adminLogin")
                    .defaultSuccessUrl("/indexAdmin")
                    .failureUrl("/errorAdminLogin").permitAll()
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/index").deleteCookies("JSESSIONID").permitAll()
                    .and().exceptionHandling().accessDeniedPage("/accessDenied");
            http.csrf().disable();

            //   http.authorizeRequests().anyRequest().permitAll();
        }

        @Bean
        public AuthenticationManager customAuthenticationManager() throws Exception {
            return authenticationManager();
        }

        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(adminDetailsService).passwordEncoder(bCryptPasswordEncoder());
        }
    }
    @Configuration
    @Order(2)
    public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/resources/**", "/index", "/indexView","/register", "/errorRegister",
                            "successRegister", "/actionBooks", "/historyBooks", "/otherBooks", "/romanceBooks",
                            "/personalDevelopmentBooks", "/scienceFictionBooks", "/showBooks", "/travelBooks").permitAll()
                    .antMatchers("/indexUser", "/addAddress", "/editAccount",
                            "/errorUser", "/successUser", "/processOrder", "/shoppingCart",
                            "/viewUserOrder", "/viewAddress").hasAuthority("user")
                  //  .anyRequest().hasAuthority("user")
                    .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
                    .defaultSuccessUrl("/toIndexUser")
                    .failureUrl("/errorLogin").permitAll()
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/index").deleteCookies("JSESSIONID").permitAll()
                    .and().exceptionHandling().accessDeniedPage("/accessDeniedAdmin");
            http.csrf().disable();

            //   http.authorizeRequests().anyRequest().permitAll();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }



        @Bean
        public AuthenticationManager customAuthenticationManager() throws Exception {
            return authenticationManager();
        }



        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        }
    }

}