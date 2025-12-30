package webcamera.com.vn.webapp.controller.Auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.annotation.Repeatable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import webcamera.com.vn.webapp.DTO.AuthDTO.AuthResponseDTO;
import webcamera.com.vn.webapp.DTO.AuthDTO.AuthRequestDTO;
import webcamera.com.vn.webapp.JWT.JwtTokenProvider;
import webcamera.com.vn.webapp.entity.User;
import webcamera.com.vn.webapp.repository.UserRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")    
    public ResponseEntity<AuthResponseDTO> Login(@RequestBody AuthRequestDTO request){
        //1. yeu cau xac thuc cua spring security -> nho no xac minh tai khoan -> tra cho user do mot token
       Authentication auth =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassWord())
       );

       //2. lay UseDetails tu doi tuong auth(username,password, role, avatar)
       UserDetails userDetails = (UserDetails) auth.getPrincipal();

       //3. sinh ra token(truyen username va role vao paylod)
       String token  = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

       //4. lay avatar vaf id tu database
       User userEntity = userRepo.findByUsername(userDetails.getUsername());
       String avatar = userEntity.getAvatar();
       Integer userId = userEntity.getId();

       //5. tra ve token kem avatar va token khi call api login 
       return ResponseEntity.ok(
               new AuthResponseDTO(token, avatar, userDetails.getUsername(), userId)
       );
        
    }
}


