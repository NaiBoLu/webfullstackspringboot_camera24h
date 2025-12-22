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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import webcamera.com.vn.webapp.JWT.JwtFilter;


/*JWTI- security config
* -> lop cau hinh bao mat, quyet dinh request nao co jwt token thi cho qua,  va dua vao jwt token voi
* phan quyen de biet user nao co role nao va permission nao de cung cap api tuowng ung
* ,-> request nao khong cos token thi no khong duyet(tuc khong cho qua)
* */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /* giai thich so dong code:
     + .csrf(AbstractHttpConfigurer::disable): tat CSRF protection, vô hiệu hóa CSRF
     của spring scurity mặc định yêu cầu token csrf cho mỗi method header(put/post/delete/options) 
     để tránh lỗi 403(không có quyền truy cập) nhưng a API rest không cần CSRF NÀY vì jwt token an toàn hơn
     + .authorizeHttpRequests:  cấu hình quyền truy cập cho từng request
     , quyết định request nào cần xác thực, request nào khong cần
     + auth -> auth.requestMatchers: auth đói tượng cấu hình quyền, requestMatchers chỉ định các 
     URL patter(danh sách đg dân api).. -> liệt kê các url không cần kiểm tra jwt token(bỏ qua luôn)


    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",  //cho phep api swagger dc phep qua cong an ninh security
                                "/v3/api-docs/**",  //cho phep api docs cua swagger dc phep qua cong an ninh
                                "/api/auth/**"
                        )
                        .permitAll()  //cho phep truy cap ma khong can kiem tra
                        .anyRequest()
                        .authenticated() //yeu cau security kiem tra cac th con lai

                )
                /*mặc định spring security tạo session trên server(lưu user infog), essionCreationPolicy.STATELESS 
                ngăn không cho lưu session trên server -> tại sao??? tại vì dùng jwt token(mang thong tin user rồi)
                 -> nên không cần lưu trong sesion*/
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /* spring security có một filter mặc định:UsernamePasswordAuthenticationFilter(kiểm tra username
                passowrd form) , khi bạn muons jwtFilter chay jtrc filter mặc đinh này
                 => thứ tự chạy: JwtFilter + UsernamePasswordAuthenticationFilter + filter khác còn lại
                 -> tại sao có đoạn này
                  + jwtfilter sẽ kiểm tra token jwt trc
                  + nếu có toekn hợp lệ -> xác thực xong, không cần kiểm tra username/passwordm, 
                  <=> ngc lại nếu token không hợp lệ -> thì từ chói request này luôn và ngay*/
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
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
