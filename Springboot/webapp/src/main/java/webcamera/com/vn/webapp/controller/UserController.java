package webcamera.com.vn.webapp.controller;
/*trung tam dieu phoi hoat dong cua mo hinh 3 lop*/


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.UserDTO.UserCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.UserDTO.UserUpdateRequestDTO;
import webcamera.com.vn.webapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //goi khoi tao lop services trong controller cua user
    @Autowired
    private UserService userService;

    /*I - render GET*/
    @GetMapping
    public ResponseEntity<Map<String, Object>> GetIndex(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                        @RequestParam(defaultValue = "3") Integer pageSize,
                                                        @RequestParam(defaultValue = "id") String sortby){
        //nho sevice goi thuc thi get all du lieu
        return userService.getAllUserPagination(pageNumber, pageSize, sortby);
    }



    /*II - POST create */
    /*
     + @PostMapping:thiet lap mapping theo chuan method post - create trong crud cua repository cuar spring boot
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> Create(@RequestParam("file")MultipartFile file,
                                                      @RequestParam("data")  String jsonData){

        //goi class ObjectMapper de mapp json(param: data) gui len -> parse json do thanh value trong dto cua usercreateReqstDTO
        ObjectMapper objectMapper = new ObjectMapper();

        //goi khoi tao lop dto cua user
        UserCreateRequestDTO objDTO = null;

        //tien hanh cho DTO doc va ghi nhan value tu json gui len da dc map thog qua lop OjectMapper
        try{
            objDTO = objectMapper.readValue(jsonData, UserCreateRequestDTO.class);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return userService.createUser(objDTO, file);
    }

    /*update - PUT*/
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id,
                                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        /*goi class ObjectMapper: de mapp json(param: data -> parse(chuyen doi) json  thanh value trong csdl*/
        ObjectMapper objectMapper = new ObjectMapper();

        //khoi t ao lop dto update
        UserUpdateRequestDTO objDTO = null;

        //tien hanh cho DTO doc va ghi nhan value json gui len da dc map thong qua lop ObjectMapper
        try{
            objDTO = objectMapper.readValue(jsonData, UserUpdateRequestDTO.class);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return userService.updateUser(id, objDTO, file);
    }

    /*
     * @PathVariable: anotation dc su dung de trich xuat gia thong qua url  api va anh xa no toi
     * tham so cua method controler nay,
     *  -> day la cach ma g ia tri cua id trong dg dan path dc truyen den tham so id cua mehotd delete
     * */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return userService.deleteUsre(id);
    }

}
