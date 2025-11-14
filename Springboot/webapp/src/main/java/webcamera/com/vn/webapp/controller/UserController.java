package webcamera.com.vn.webapp.controller;
/*trung tam dieu phoi hoat dong cua mo hinh 3 lop*/


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Map<String, Object>> Create(@RequestBody @Valid UserCreateRequestDTO res){
        //xu ly bat loi nem ra throw tu serive bang cach dung try catch
        try{
            //goi den service luu csdl tu dto(dc gui len tu client thong qua create form dang ky user)
            return userService.createUser(res);
        }catch(Exception ex){
            //khoi tao bien luu  response trve
            Map<String, Object> response = new HashMap<>();
            response.put("data", ex.getMessage());
            response.put("statuscode", 501);
            response.put("msg","du lieu co loi vui long kiem tra lai");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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


    /*update - PUT*/
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody UserUpdateRequestDTO res){
        return userService.updateUser(id, res);
    }


}
