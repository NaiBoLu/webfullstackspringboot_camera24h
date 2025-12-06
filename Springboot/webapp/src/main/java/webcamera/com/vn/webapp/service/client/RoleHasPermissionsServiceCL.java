package webcamera.com.vn.webapp.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.client.RoleHasPermissionsDTO_CL.RoleHasPerCreateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.RoleHasPermissionsDTO_CL.RoleHasPerUpdateRequestDTO_CL;
import webcamera.com.vn.webapp.entity.RoleHasPermissions;
import webcamera.com.vn.webapp.repository.RoleHasPermissionRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleHasPermissionsServiceCL {
    @Autowired
    private RoleHasPermissionRepository roleHasPermissionRepo;

    //getall co phan trang
    public ResponseEntity<Map<String, Object>> getAllRoleHasPermission(Integer pageNumber, Integer pageSize, String sortBy){
        // tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortBy)); // yeu cau
        Page<RoleHasPermissions> pageResult = roleHasPermissionRepo.findAll(pageable); // goi repo lay ket qua tat ca

        //neu co noi dung
        if(pageResult.hasContent()){
            //tra ket qua ve response
            response.put("data",pageResult.getContent());
            response.put("statuscode",201);
            response.put("msg","tra ve ket qua thanh cong oh yeah");

            response.put("currentpage",pageNumber);
            response.put("Nextpage",pageResult.hasNext());
            response.put("permisionpage",pageResult.hasPrevious());
            response.put("isFirst",pageResult.isFirst());
            response.put("isLast",pageResult.isLast());
            response.put("TotalPage",pageResult.getTotalPages());
            response.put("TotalElement",pageResult.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg","khong tim thay du lieu huhuhu");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /*II - Post(create)*/
    public ResponseEntity<Map<String, Object>> createRoleHasPermission(RoleHasPerCreateRequestDTO_CL objcreate ){
        //response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //c-1 khoi tao UserEntity
        RoleHasPermissions newEntity = new RoleHasPermissions();

        newEntity.setRoleId(objcreate.getRoleId());
        newEntity.setPermissionId(objcreate.getPermissionId());

        //nhờ repo luu lại vào db
        RoleHasPermissions createEntity = roleHasPermissionRepo.save(newEntity);

        //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","create thanh cong oh yeah");

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //update
    public ResponseEntity<Map<String, Object>> updateRoleHasPermission(Integer id, RoleHasPerUpdateRequestDTO_CL objUpdate){
        //tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //tim kiem entity theo id
        Optional<RoleHasPermissions> optFound = roleHasPermissionRepo.findById(id);

        //neu tim thay thi lay ra update
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            RoleHasPermissions entityEdit = optFound.get();

            //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
            if(objUpdate.getRoleId() != null){
                entityEdit.setRoleId(objUpdate.getRoleId());
            }
            if(objUpdate.getPermissionId() != null ){
                entityEdit.setPermissionId(objUpdate.getPermissionId());
            }

            //goi repoluu lai cap nhat
            RoleHasPermissions updatedEntity = roleHasPermissionRepo.save(entityEdit);

            //goi response tra ve ket qua
            response.put("data",updatedEntity);
            response.put("statuscode",200);
            response.put("msg","update thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{

            //goi response tra ve ket qua
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","update ko thanh cong huhuhu");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    //delete xoa
    public ResponseEntity<Map<String, Object>> deleteRoleHasPermission(Integer id){
        //tao response luu ket qua tra ve
        Map<String,Object> response = new HashMap<>();

        //tim theo id
        Optional<RoleHasPermissions> optFound = roleHasPermissionRepo.findById(id);

        //neu tim thay thi xoa
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            RoleHasPermissions deleteEntity = optFound.get();

            //goi repo xoa entity
            roleHasPermissionRepo.delete(deleteEntity);

            //goi response luu ket qua tra ve
            response.put("data",null);
            response.put("statuscode",200);
            response.put("msg","xoa thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            //goi response luu ket qua tra ve
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","xoa ko thanh cong oh no");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

}
