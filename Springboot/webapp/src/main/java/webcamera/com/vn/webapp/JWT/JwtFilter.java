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

        /*tat ca request con lai -> trong header phai co Authroziation thi moi xu ly */
        final String authHeader = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        /*chuan Authorization: Bearer XXXYYYZZZZ..., Phai bat dau chuoi bang chu "Bearer"  thi moi xu ly con khong
        * khong xu ly -> nd cua jwt token chua la phai dong chu "Bearer .. xxxyyyzz" phai bat buoc co chu Bearer o dau
        * chuoi*/
        if(authHeader != null && authHeader.startsWith("Beater")){
            /*doan duoi: chuan jwt toekn la "Bearer  <lay 7 ky tu>" */
            jwt = authHeader.substring(7);
            //goi jwtprovider -> giai ma token lay username cua User request len server
            userName = jwtProvider.extractToken(jwt);
        }
    }
}


