package webcamera.com.vn.webapp.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import webcamera.com.vn.webapp.JWT.JwtFilter;


/*JWTI- security config 
* -> lop cau hinh bao mat, quyet dinh request nao co jwt token thi cho qua,  va dua vao jwt token voi
* phan quyen de biet user nao co role nao va permission nao de cung cap api tuowng ung
* ,-> request nao khong cos token thi no khong duyet(tuc khong cho qua)
* */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    /* goi cau hinh client.url port 3000 */
    @Value("${client.url}")
    private String clientUrl;

    /* giai thich so dong code:
     + .cors: bat CORS(cho phep browser tu domain khacs goi api cua minh )
      ++ Customizer.withDefaults(): su dung cau hinh CORS mac dinh cua spring security
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
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",  //cho phep api swagger dc phep qua cong an ninh security
                                "/v3/api-docs/**",  //cho phep api docs cua swagger dc phep qua cong an ninh
                                "/api/auth/**",
                                "/uploads/**",
                                "/api/client/users/create/**",
                                "/api/client/users/active-account/**"
                        )
                        .permitAll()  //cho phep truy cap ma khong can kiem tra
                        //quan trong: security phai cho phep OPTIOND request di qua khong can token de tranh loi prelight
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated() //yeu cau security kiem tra cac th con lai

                )
                /*mặc định spring security tạo session trên server(lưu user infor), sessionCreationPolicy.STATELESS 
                ngăn không cho lưu session trên server -> tại sao??? tại vì dùng jwt token(mang thong tin user rồi)
                 -> nên không cần lưu trong sesion*/
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /* spring security có một filter mặc định:UsernamePasswordAuthenticationFilter(kiểm tra username
                passowrd form) , khi bạn muốn jwtFilter chạy trc filter mặc đinh này
                 => thứ tự chạy: JwtFilter + UsernamePasswordAuthenticationFilter + filter khác còn lại
                 -> tại sao có đoạn này
                  + jwtfilter sẽ kiểm tra token jwt trc
                  + nếu có toekn hợp lệ -> xác thực xong, không cần kiểm tra username/passwordm, 
                  <=> ngc lại nếu token không hợp lệ -> thì từ chói request này luôn và ngay*/
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    /*cau hinh AuthenticationManager nhu mot bean khac phuc loi AuthenticationManager khong the tu tiem phu thuoc autowired truc tiep bang annotation @Autowired nhu cac bean khac*/
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



    /*cấu hình CORS
     -> giải quyết lỗi Preligt khi gọi API từ trình duyệt(nextjs )
     -> khi gọi API kèm theo t oken trong header, trình duyệt sẽ gửi cả request OPTIONS để thằm dò
     -> xem coi request gửi các yêu cầu api của các method header(put/get/post/delete) phải kèm theo 
     options để thăm dò xác định có đumgs method header đó đc gửi đúng không -> ok thì mới cho call, trành 
     lõi prelight do server không xác định đc chính xác đc api gọi lên có đúng là method header cần gọi không 
     dù lúc đó đã có  token kèm thao
     ==> bean này đảm bảo security chấp nhận yêu cawuf tham do OPTIONS này mà không bị chối
    */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
       CorsConfiguration corsConfiguration = new CorsConfiguration(); 
       //cho phep nguon (Origin) tu phia frontend cua ban
       corsConfiguration.setAllowedOrigins(Arrays.asList(clientUrl));
       //cho phep cac phuong thuc HTTP can thiet bao gom ca OPTIONS DI KEM
       corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
       //cho phep cac Header quan trong dawc biet la 'Authorization' chua token jwt
       corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
       //cho phpe gu kem thon tin xac thuc(cookies hoac auth header)
       corsConfiguration.setAllowCredentials(true);

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       //ap dung cau hinh tren cho toan bo cac api khi request gui len
       source.registerCorsConfiguration("/**", corsConfiguration);

       return source;
    }
    
}
