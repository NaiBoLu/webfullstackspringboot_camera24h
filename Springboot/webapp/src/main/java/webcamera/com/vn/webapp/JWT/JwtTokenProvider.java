package webcamera.com.vn.webapp.JWT;


/*JWTIII - lop nay dung de tao ma hoa thong tin duoi dang jwt token(tao ra token chuan)
*  -> chinh xac hon la tao token & giai ma token
*  => quy dinh Header va payload, cung nhu xac thuc chu ky signature
*  => JwtProvider -> dong vai  tro nhu mot nha may san xuat va gia ma Token
*   + tao token khi user login
*   + tien hanh gia ma  token khi user goi api
*   + kiem tra token hop le khong
*
* >>quy trinh kiem tra<<
*  -> kiem tra username/password tren csdl dung khong
*  -> meu dung ok roi -> generateToken: khoi tao va gia ma token
* */

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider{
   /*phần signature tạo chứ ký xác thực
    -> vai  tro:
     + dùng kế ký và xác minh jwt bằng thuật toán HMAC SHA-256(HS256)
    -> cách hoạt động
     + khi gọi signWIth(key, SignatureAlgorithm.HS256)
     + thư viện jwt sẽ
       ++ lấy header + payload
       ++ dùng thuật toán HS256
       ++ kết hợp với SECRET KEY(biến key))
       --> sinh ra phần signature của jwt(chữ ký của jwt token)
   */
    private static final String JWT_SECRET = "my-jwt-secret-key-32-bytes-long!!";

    //tao doi tnggn key chuan thuat toan HS256 tu chuoi JWT_SECRET tren
    private final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

    /*encode - tao  sinh ra token(tao ra token voi dinh dang da ma hoa encrypt)*/
    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities){
        //1. tao thoi gian song cho no - thoi gian het han  token tu khi sinh ra
        Date now  = new  Date();
        Date expiry = new Date(now.getTime() + 86400000); //hieu ms -> quy doi  tat ca time thanh ms(24*60*60*1000 = ms)

        //tao mot chuoi danh sach role tu collection authorities
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        /*giai thich code:
        *  -> builder: tien khoi tao
        *  -> setSubject(username): claim1 - dat chu de cua token, gia tri nay thg la ten ngui dung hoac id
          -> setIssuedAt(now): claim2 - thoi gian tao, dat thoi diem hien tai khi tao ra token
          -> etExpiration(expiry): claim 3 - thoi gian het han - het thoi song thi token mat hieu luc
          ->  .signWith(Keys.hmacShaKeyFor(jwtSecrets.getBytes()), SignatureAlgorithm.HS256):
          *  + signWith: thuc hien buoc ky ten  token tao chu ky (signature)
          *  + Keys.hmacShaKeyfor: tao khoa ma hoa tu chuoi bi mat do may tao
          *  + SignatureAlgorithm: chi dinh thuat toan dc sung dung de tao token la HMAC ussing HS256
          *  + compact(): hoan tat viec xay dung va chuyen jwt thanh mot chuoi nen da dc ma hoa base64URL
          * cos dinh dang la chuan token: Header.Payload.signature: vd Bearer xxaaa.ffefdfasf.dfdfdifjd
       */
        return Jwts.builder()
                .setSubject(username)
                .claim("role", roles) //them dong nay de luu role cua user vao trong payload cua token
                .setIssuedAt(now)
                .setExpiration(expiry)
                //.signWith(Keys.hmacShaKeyFor(jwtSecrets.getBytes()), SignatureAlgorithm.HS256)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /*decode - giai ma token(decrypt giai token lay thong tin)*/
    public String extractToken(String token){

        /*giai thich code:
        * -> parserBuilder: bat dau qua trinh chuyen doi token(bcrypt giai ma token)
        * ->  .setSigningKey(jwtSecrets.getBytes()): dat khoa bi mat(Secretkey), giup xac minh chu ky
        * cua token.. neu chu ky ma khong khop( token gia mao qua trinh nay coi nhu huy)
        * -> build: hoan tat viec cau hinh bo giai ma, tao ra doi tuong jwtParser(giai ma  token ra)
        * -> parseClaimsJwt(token): thuc hien gia ma va xac minh token
        *   + giai ma header vaf payload cua token
        *   + dung khoa bi mat da thiet lap de kiem tra chu ky(signature)
        *   + kiem tra xem token con hieu luc thoi dian khoi tao khong
        *   --> neu ma token khong hop le(het han, chu ky sai) no se nem ra ngoai loi va kethuc qua trinh nay
        *
        * -> getBody(): lay ra phan payload cua token, de lay ra thong tin can lay dc dc ma hoa(user...)
        * -> etSubject(): trich xuat gia tri cua claim subject tu payload, gia tri nay chinh la thong tin username ma
        * minh dat khi tao ra token -> tra ve username
        * */
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /*ham kiem tra token hop le hay khong*/
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractToken(token).equals(userDetails.getUsername());
    }

}
