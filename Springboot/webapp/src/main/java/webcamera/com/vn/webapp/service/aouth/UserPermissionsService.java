package webcamera.com.vn.webapp.service.aouth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import webcamera.com.vn.webapp.DTO.AuthDTO.AuthUserHasPermisionResponseDTO;
import webcamera.com.vn.webapp.repository.PermissionRepository;

@Service
public class UserPermissionsService {
    
    @Autowired
    private PermissionRepository permissionRepo;

    /* method tra ve list cac quyen permission cho user */ 
    public ResponseEntity<Map<String, Object>> getPermission(String username){
        //. khoi tao lowp luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //goi repository lay danh dach quyen
        List<Object[]> resultPermissions = permissionRepo.findPermissionsRawByUsername(username);

        /*chuyen doi (maping) du lieu*/
        List<AuthUserHasPermisionResponseDTO> listPermissionByUserName = resultPermissions.stream().map(obj -> new AuthUserHasPermisionResponseDTO(
            (String) obj[0], //username
            (String) obj[1], //permission name
            (String) obj[2] //displayname
        ))
        .collect(Collectors.toList());

        // tra ve ket qua va luu vao localstorage
        if(listPermissionByUserName != null || listPermissionByUserName.size() > 0){
            response.put("data", listPermissionByUserName);
            response.put("status code", 200);
            response.put("msg", "get permissions for username success oh yeah baby more!");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data", null);
            response.put("status code", 404);
            response.put("msg", "get permissions for username failed oh yeah baby more!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
