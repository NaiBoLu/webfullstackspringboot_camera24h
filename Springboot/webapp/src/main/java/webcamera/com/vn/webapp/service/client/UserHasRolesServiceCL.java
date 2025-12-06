package webcamera.com.vn.webapp.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.client.UserHasRolesDTO_CL.UserHasRoleCreateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.UserHasRolesDTO_CL.UserHasRoleUpdateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.UserHasRolesDTO_CL.UserHasRolesBatchCreateRequestDTO_CL;
import webcamera.com.vn.webapp.entity.UserHasRoles;
import webcamera.com.vn.webapp.repository.UserHasRolesRepository;

import java.util.*;

@Service
public class UserHasRolesServiceCL {
    @Autowired
    private UserHasRolesRepository userHasRolesRepo;

    //getall co phan trang
    public ResponseEntity<Map<String, Object>> getAllUserHasRole(Integer pageNumber, Integer pageSize, String sortBy){
        // tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortBy)); // yeu cau
        Page<UserHasRoles> pageResult = userHasRolesRepo.findAll(pageable); // goi repo lay ket qua tat ca

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

    /*II - 0: Post(create)*/
    public ResponseEntity<Map<String, Object>> createUserHasRole(UserHasRoleCreateRequestDTO_CL objcreate ){
        //response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //c-1 khoi tao UserEntity
        UserHasRoles newEntity = new UserHasRoles();

        newEntity.setUserId(objcreate.getUserId());
        newEntity.setRoleId(objcreate.getRoleId());

        //nhờ repo luu lại vào db
        UserHasRoles createEntity = userHasRolesRepo.save(newEntity);

        //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","create thanh cong oh yeah");

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    /*II - 1: Post(create batch nhieu role cho user)*/
    public ResponseEntity<Map<String, Object>> batchCreateUserHasRoles(UserHasRolesBatchCreateRequestDTO_CL objCreate){
        // tao map response luu tru
        Map<String, Object> response = new HashMap<>();

        //goiu repository create nhieu role cho mot user
        List<UserHasRoles> listCreateEmtoty = new ArrayList<>();
        try{
            for(Integer roleId : objCreate.getListRoleId()){
                UserHasRoles userHasRoles = new UserHasRoles();
                userHasRoles.setUserId(objCreate.getUserId());
                userHasRoles.setRoleId(roleId);

                userHasRolesRepo.save(userHasRoles);
                listCreateEmtoty.add(userHasRoles);
            }
        } catch (Exception e) {
            throw e;
        }

        //tra ve ket qua chuan rest full api
        response.put("data", listCreateEmtoty);
        response.put("statuscode", 201);
        response.put("msg", "create thanh cong");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //update
    public ResponseEntity<Map<String, Object>> updateUserHasRole(Integer id, UserHasRoleUpdateRequestDTO_CL objUpdate){
        //tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //tim kiem entity theo id
        Optional<UserHasRoles> optFound = userHasRolesRepo.findById(id);

        //neu tim thay thi lay ra update
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            UserHasRoles entityEdit = optFound.get();

            //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
            if(objUpdate.getUserId() != null ){
                entityEdit.setUserId(objUpdate.getUserId());
            }
            if(objUpdate.getRoleId() != null){
                entityEdit.setRoleId(objUpdate.getRoleId());
            }


            //goi repoluu lai cap nhat
            UserHasRoles updatedEntity = userHasRolesRepo.save(entityEdit);

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
    public ResponseEntity<Map<String, Object>> deleteUserHasRole(Integer id){
        //tao response luu ket qua tra ve
        Map<String,Object> response = new HashMap<>();

        //tim theo id
        Optional<UserHasRoles> optFound = userHasRolesRepo.findById(id);

        //neu tim thay thi xoa
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            UserHasRoles deleteEntity = optFound.get();

            //goi repo xoa entity
            userHasRolesRepo.delete(deleteEntity);

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
