package webcamera.com.vn.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                "/swagger-ui/**",  //cho phep api swagger dc phep qua cong an ninh security
                                         "/v3/api-docs/**",  //cho phep api docs cua swagger dc phep qua cong an ninh
                                         "/api/client/users/create/**",
                                         "/api/client/users/**",
                                         "/api/client/userhasrole/batch-create/**",
                                         "/api/client/userhasrole/**"
                        )
                        .permitAll()  //cho phep truy cap ma khong can kiem tra
                        .anyRequest()
                        .authenticated() //yeu cau security kiem tra cac th con lai

                )
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /*cau hinh AuthenticationManager nhu mot bean khac phuc loi AuthenticationManager khong the tu tiem phuj
    * thuoc autowired truc tiep bang annotation @Autowired nhuw cac bean khac*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }

    /*xu ly ma hoa mat khau bag thuat toan bcypt cuar dependencies security cuar spring boot
    * -> viec nay giup tranh loi "you have entered a password with no PasswordEncoder
    * -> phuc vu cong tac xac thuc phan quyenf???"*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
