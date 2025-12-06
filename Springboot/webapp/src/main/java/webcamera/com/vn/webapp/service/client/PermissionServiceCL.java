package webcamera.com.vn.webapp.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.client.PermissionDTO_CL.PermissionCreateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.PermissionDTO_CL.PermissionUpdateRequestDTO_CL;
import webcamera.com.vn.webapp.entity.Permission;
import webcamera.com.vn.webapp.repository.PermissionRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class PermissionServiceCL {
    @Autowired
    private PermissionRepository permissionRepo;

    // getall co phan trang
    public ResponseEntity<Map<String, Object>> getAllPermission(Integer pageNumber, Integer pageSize, String sortBy){
        // tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortBy)); // yeu cau
        Page<Permission> pageResult = permissionRepo.findAll(pageable); // goi repo lay ket qua tat ca

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
    public ResponseEntity<Map<String, Object>> createPermission(PermissionCreateRequestDTO_CL objcreate ){
        //response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //c-1 khoi tao UserEntity
        Permission newEntity = new Permission();

        newEntity.setName(objcreate.getName());
        newEntity.setDisplayName(objcreate.getDisplayName());
        newEntity.setGuardName(objcreate.getGuardName());

        //nhờ repo luu lại vào db
        Permission createEntity = permissionRepo.save(newEntity);

        //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","create thanh cong oh yeah");

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //update
    public ResponseEntity<Map<String, Object>> updatePermission(Integer id, PermissionUpdateRequestDTO_CL objUpdate){
        //tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //tim kiem entity theo id
        Optional<Permission> optFound = permissionRepo.findById(id);

        //neu tim thay thi lay ra update
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            Permission entityEdit = optFound.get();

            //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
            if(objUpdate.getName() != null && !objUpdate.getName().isEmpty()){
                entityEdit.setName(objUpdate.getName());
            }
            if(objUpdate.getDisplayName() != null && !objUpdate.getDisplayName().isEmpty()){
                entityEdit.setDisplayName(objUpdate.getDisplayName());
            }
            if(objUpdate.getGuardName() != null && !objUpdate.getGuardName().isEmpty()){
                entityEdit.setGuardName(objUpdate.getGuardName());
            }

            //goi repoluu lai cap nhat
            Permission updatedEntity = permissionRepo.save(entityEdit);

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
    public ResponseEntity<Map<String, Object>> deletePermission(Integer id){
        //tao response luu ket qua tra ve
        Map<String,Object> response = new HashMap<>();

        //tim theo id
        Optional<Permission> optFound = permissionRepo.findById(id);

        //neu tim thay thi xoa
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            Permission deleteEntity = optFound.get();

            //goi repo xoa entity
            permissionRepo.delete(deleteEntity);

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
