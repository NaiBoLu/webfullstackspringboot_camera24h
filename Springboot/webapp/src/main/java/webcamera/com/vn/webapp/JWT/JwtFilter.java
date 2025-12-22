package webcamera.com.vn.webapp.JWT;

/*JWTII - jwt filter co tac dung xac thuc va uy quyen
* -> nhiem vu chinh:
*  + chan moi request khong co jwt
*  + tach token: phan tach va gia ma token: header, payload, signature
*  + kiem tra valid: jwt token
*  + lay user tu db: thong them lop nua la UserdetailServiceImpl.. choc vao csdl lay role va permission
*  + xac thuc user vao securitycontext
*
* >>note>>
*  -> qua trinh login tai khoan(username/password) thi khong can jwtfilter
*  -> ngoai tru login con lai deu can jwtFilter loc kiem tra  token
*
* >>neu ma khong lop jwtfilter vaf token thi sao>><<
*  -> lop loc jwtfilter giup tao mot cong an ninh loc va loai bien nhung  request tu nguoi
* dung nao dc gui len ma khong jwt token - sau khi ma dat yeu cau sinh ra token - viec tiep
* theo sang loc xu ly giao cho securityconfig xu ly (jwtFilter chi co nhiem la lop loc nhung ai
* ma khong jwt token thi khong cho qua)
* => jwtFilter chi thuc hien: lay jwt -> validate(xac thuc) -> setAuthentication(cap quyen)
* => luu y:
*  + no chi kiem tra mot lan duy nhat, vi neu khong jwt token thi da loai bien ngay tu dau
* cho nen no dau can lam nhieu lan
* */

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*OncePerRequestFilter: lop lib cua spring booot security: loc kiem tra request mot lan khi chay*/
@Component()
public class JwtFilter  extends OncePerRequestFilter {
    //JwttokenProvider: dinh thanh cau tao nen jwt token
    @Autowired
    private JwtTokenProvider jwtProvider;

    //UserDetailsServiceImpl: choc thang vao csdl: User so sanh xac thuc truy van role va permision trong csdl
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*method xu ly nghiep vu jwtfilter: loc kiem tra co token hay khong va uy quyen cho no*/
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /* xu ly cho phep cac lop dat cach tu securtyconfig liet ke khong can kiem tra token de jwtFilter biet va cho qua */                                        
         String path = request.getRequestURI();     
         if(path.startsWith("/swagger-ui/")
            || path.startsWith("/v3/api-docs/")
            || path.startsWith("/api/auth/login")
            ){        
                filterChain.doFilter(request, response);
                return;
         }                        


        /*tat ca request con lai -> trong header phai co Authroziation thi moi xu ly */
        final String authHeader = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        /*chuan Authorization: Bearer XXXYYYZZZZ..., Phai bat dau chuoi bang chu "Bearer"  thi moi xu ly con khong
        * khong xu ly -> nd cua jwt token chua la phai dong chu "Bearer .. xxxyyyzz" phai bat buoc co chu Bearer o dau
        * chuoi*/
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            /*doan duoi: chuan jwt toekn la "Bearer  <lay 7 ky tu>" */
            jwt = authHeader.substring(7);
            //goi jwtprovider -> giai ma token lay username cua User request len server
            userName = jwtProvider.extractToken(jwt);
        }

        /* kiem tra username trong token luu tru ở localstorage và username trong csdl có trung khop khong nếu ok thì xư lý phan quyen 
        >>Ý nghĩa của việc kiểm tra == null(SecurityContextHolder.getContext().getAuthentication() == null))
            -> Khi bạn kieemr tra dieu kien nay bạn đang thực hiện một kiểm tra an toàn:
                + Xác định trạng thái đăng nhập: Nếu kết quả là null, nghĩa là yêu cầu này chưa được 
                xác thực (người dùng chưa đăng nhập hoặc là khách truy cập ẩn danh).

                + Tránh lỗi NullPointerException: Trước khi truy cập vào tên người dùng (getName()) 
                hoặc quyền hạn, bạn phải đảm bảo đối tượng Authentication tồn tại để ứng dụng không 
                bị "văng" lỗi.

                + Xử lý Logic nghiệp vụ: * Nếu null: Chuyển hướng người dùng sang trang Login.
                Nếu không null: Cho phép thực hiện các hành động nhạy cảm hoặc ghi log "Ai là người 
                thực hiện thao tác này".
        */
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            //kiem tra xem token valid khong?
            if(jwtProvider.isTokenValid(jwt, userDetails)){
                //taoj authentication dat vao securitycontext(kiem tra userco ton tai khong hop le khong va co role, per gi -> di tiep or khong?)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        /* day là mọt câu lệnh đặc biệt trong cấu trúc bảo mật của spring security
         -> quy trình hoạt động như sau:
          + bộ lộc filter chain xử lý trc kiểm tra hợp lệ token rồi mới cho đi qua đến các tầng controller/serlvet khác
          + Filterchain: hành động này cho phép các request tiếp tục đc đi qua các bộ lọc spring security khác và cuối cùng 
          đến đc controller của bạn
          
          ## nếu không có dòng filterchain.doFilter này thì chuyện gì xảy ra ## 
          -> nếu không gọi filterChain.doFilter(request, response): chuỗi cử lỹ sẽ bị ngắt ngay tại filter hiện tại 
          -> kết quả: các yêu cầu từ client request gửi lên  sẽ không bao giờ đến  đến đc controller đích, dẫn đến 
          người dùng không thể truy cập đc tài nguyên
          <=> note: trừ phi muốn chặn yêu cầu (vd: request bị từ chối do không có jwt hoặc jwt không hợp lệ)*/ 
        filterChain.doFilter(request, response);
    }
}


