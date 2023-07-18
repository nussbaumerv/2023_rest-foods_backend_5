package ch.noseryoung.blj.restfoods.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return new ProviderManager(provider);
    }//Authentifizierung von Benutzern in einer Spring-Sicherheitskonfiguration zu verwalten.

    /*filter chain which can be matched with an HTTP request */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()//welche Requests überprüft werden sollen
                //set security by urls
                .antMatchers("/api/v1/**").permitAll()//für alle nutzer zugänglich
                .anyRequest()// alle Requests müssen zufgelalssen werden um authentifiziert werden sollten
                .authenticated()
                .and()
                .httpBasic() //aktiviert die authentifizierung
                .and()
                .csrf().disable()
                .cors().disable()// schuzt vor CSRF udn CORS(Anwendung nicht mit anderen Domänen zusammenarbeiten soll)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //never creates an HTTP session

        return http.build();
    }
}
