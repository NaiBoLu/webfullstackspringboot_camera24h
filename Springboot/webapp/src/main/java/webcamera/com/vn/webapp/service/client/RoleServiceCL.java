package webcamera.com.vn.webapp.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.client.RoleDTO_CL.RoleCreateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.RoleDTO_CL.RoleUpdateRequestDTO_CL;
import webcamera.com.vn.webapp.entity.Role;
import webcamera.com.vn.webapp.repository.RoleRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleServiceCL {
    @Autowired
    private RoleRepository roleRepo;

    //getall co phan trang
    public ResponseEntity<Map<String, Object>> getAllRole(Integer pageNumber, Integer pageSize, String sortBy){
        // tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortBy)); // yeu cau
        Page<Role> pageResult = roleRepo.findAll(pageable); // goi repo lay ket qua tat ca

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

    //create tao role
    public ResponseEntity<Map<String, Object>> createRole(RoleCreateRequestDTO_CL objCreate){
        //tạo response luu kq tra ve
        Map<String, Object> response = new HashMap<>();

        //chuyen dto thanh entity
        Role newEntity = new Role();

        newEntity.setName(objCreate.getName());
        newEntity.setDisplayName(objCreate.getDisplayName());
        newEntity.setGuardName(objCreate.getGuardName());

        //nhờ repo luu lại vào db
        Role createEntity = roleRepo.save(newEntity);

        //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","create thi create lam deo gi cang");

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //update role
    public ResponseEntity<Map<String, Object>> updateRole(Integer id,RoleUpdateRequestDTO_CL objUpdate){
        //tao response luu kq tra ve
        Map<String, Object> response = new HashMap<>();

        //tim kiem update theo id
        Optional<Role> optFound = roleRepo.findById(id);

        //neu tim thay thi cap nhat ko thi thong bao 404
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            Role entityEdit = optFound.get();

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
            Role updatedEntity = roleRepo.save(entityEdit);

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

    //delete xoa role
    public ResponseEntity<Map<String, Object>> deleteRole(Integer id){
        //tao response luu ket qua tra ve
        Map<String,Object> response = new HashMap<>();

        //tim theo id
        Optional<Role> optFound = roleRepo.findById(id);

        //neu tim thay thi xoa
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua opt
            Role deleteEntity = optFound.get();

            //goi repo xoa entity
            roleRepo.delete(deleteEntity);

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
