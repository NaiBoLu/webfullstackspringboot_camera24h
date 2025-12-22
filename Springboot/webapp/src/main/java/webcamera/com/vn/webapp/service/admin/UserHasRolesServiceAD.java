package webcamera.com.vn.webapp.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import webcamera.com.vn.webapp.DTO.admin.UserHasRolesDTO_AD.UserHasRoleCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.UserHasRolesDTO_AD.UserHasRoleUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.UserHasRolesDTO_AD.UserHasRolesBatchCreateRequestDTO_AD;
import webcamera.com.vn.webapp.entity.Role;
import webcamera.com.vn.webapp.entity.User;
import webcamera.com.vn.webapp.entity.UserHasRoles;
import webcamera.com.vn.webapp.repository.RoleRepository;
import webcamera.com.vn.webapp.repository.UserHasRolesRepository;
import webcamera.com.vn.webapp.repository.UserRepository;

import java.util.*;

@Service
public class UserHasRolesServiceAD {
    @Autowired
    private UserHasRolesRepository userHasRolesRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

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
    public ResponseEntity<Map<String, Object>> createUserHasRole(UserHasRoleCreateRequestDTO_AD objcreate ){
        //response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //c-1 khoi tao UserEntity
        UserHasRoles newEntity = new UserHasRoles();

        /*cach viet cu khi khong dung annotation lk khoa ngoai: OneTOMany, ManyToOne...*/
//        newEntity.setUserId(objcreate.getUserId());
//        newEntity.setRoleId(objcreate.getRoleId());

        /*cach viet moi khi co dung lk khoa ngoai: OneTOMany, ManyToOne...*/
        User user = userRepo.findById(objcreate.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepo.findById(objcreate.getRoleId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));

        newEntity.setUser(user);
        newEntity.setRole(role);

        //nhờ repo luu lại vào db
        UserHasRoles createEntity = userHasRolesRepo.save(newEntity);

        //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","create thanh cong oh yeah");

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    /*II - 1: Post(create batch nhieu role cho user)*/
    public ResponseEntity<Map<String, Object>> batchCreateUserHasRoles(UserHasRolesBatchCreateRequestDTO_AD objCreate){
        // tao map response luu tru
        Map<String, Object> response = new HashMap<>();

        //goiu repository create nhieu role cho mot user
        List<UserHasRoles> listCreateEmtoty = new ArrayList<>();
        try{
            for(Integer roleId : objCreate.getListRoleId()){
                UserHasRoles userHasRoles = new UserHasRoles();

                User user = userRepo.findById(objCreate.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                Role role = roleRepo.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found"));

                userHasRoles.setUser(user);
                userHasRoles.setRole(role);

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
    public ResponseEntity<Map<String, Object>> updateUserHasRole(Integer id, UserHasRoleUpdateRequestDTO_AD objUpdate){
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
               User user = userRepo.findById(objUpdate.getUserId())
                       .orElseThrow(() -> new RuntimeException("New userid not found"));

                 // capnhat goi tuong entity userid
                entityEdit.setUser(user);
            }
            if(objUpdate.getRoleId() != null){
                Role role = roleRepo.findById(objUpdate.getRoleId())
                        .orElseThrow(() -> new RuntimeException("New roleid not found"));

                // capnhat goi tuong entity userid
                entityEdit.setRole(role);
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
