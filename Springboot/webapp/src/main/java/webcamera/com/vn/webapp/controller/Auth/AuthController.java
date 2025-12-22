package webcamera.com.vn.webapp.controller.Auth;

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
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtUtil;

    @PostMapping("/login")    
    public ResponseEntity<AuthResponseDTO> Login(@RequestBody AuthRequestDTO request){
        //1. yeu cau xac thuc cua spring security -> nho no xac minh tai khoan -> tra cho user do mot token
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.userName, request.passWord));

        //2. sinh mot token
        String token = jwtUtil.generateToken(request.userName);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}


