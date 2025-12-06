package webcamera.com.vn.webapp.controller.client;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webcamera.com.vn.webapp.DTO.client.RoleHasPermissionsDTO_CL.RoleHasPerCreateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.RoleHasPermissionsDTO_CL.RoleHasPerUpdateRequestDTO_CL;
import webcamera.com.vn.webapp.service.client.RoleHasPermissionsServiceCL;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/rolehaspermission")
public class RoleHasPermissionControllerCL {
    @Autowired
    private RoleHasPermissionsServiceCL roleHasPermissionsServiceCL;

    //getall
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortBy){
        // goi service thuc hien truy van hien thi tat ca thong tin cua table user co phan trang
        return roleHasPermissionsServiceCL.getAllRoleHasPermission(pageNumber, pageSize, sortBy);
    }

    //create permission
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody RoleHasPerCreateRequestDTO_CL objCreate){
        try{
            return roleHasPermissionsServiceCL.createRoleHasPermission(objCreate);
        }catch(Exception ex){
            Map<String, Object> response = new HashMap<>();

            response.put("data", ex.getMessage());
            response.put("statuscode",500);
            response.put("msg","co loi vui long xem lai huhuhu");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update pẻmission
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable(value = "id") Integer id, @RequestBody RoleHasPerUpdateRequestDTO_CL objUpdate){
        return roleHasPermissionsServiceCL.updateRoleHasPermission(id, objUpdate);
    }

    //delete permission
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable(value = "id") Integer id){
        return roleHasPermissionsServiceCL.deleteRoleHasPermission(id);
    }
}
