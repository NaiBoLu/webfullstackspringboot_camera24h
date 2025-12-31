package webcamera.com.vn.webapp.controller.Auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webcamera.com.vn.webapp.service.aouth.UserPermissionsService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/authorization")
public class AuthGetPermissionsController {
    @Autowired
    private UserPermissionsService userPermissionsService;

    @PostMapping("/getListPermissionByUserName")
    public ResponseEntity<Map<String, Object>> getPermissions(@RequestParam String username){
        //nho service thuc hien tra ve ds quyen luu vao localstorage
        return userPermissionsService.getPermission(username);
    }
    
}
