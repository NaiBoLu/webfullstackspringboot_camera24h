package camera24h.com.vn.controller;
/*trung tam dieu phoi hoat dong cua mo hinh 3 lop*/

import camera24h.com.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
