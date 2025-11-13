package camera24h.com.vn.service;

import camera24h.com.vn.entity.User;
import camera24h.com.vn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*lop luan ly logic code*/

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    /*I - GET ->lay va do du lieu co phan trang*/
    public ResponseEntity<Map<String, Object>> getAllUserPagination(int pageNumber, int pageSize, String sortby){
        //a - khoi tao bien respone luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //b- yeu cau repository lay du lieu -> goi den repository goi den thao tac crud
        /*
        * Pageable: la mot giao dien trong spring data dc su dung de ho tro phan trang
        * sap xep trang
        *  + pageNUmber: trang so may(trang dang xem)
        *  + pageSize:  tong so luong trang
        *  + sortBy: sap xep trang cot nao: id or theo ten name...
        * */
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(sortby));
        Page<User> pageResult = userRepo.findAll(pageable);
        if(pageResult.hasContent()){
            //tra ket qua cho nguoi dung -> tra theo chuan restfull APi sieu cap vip pro
            response.put("data", pageResult.getContent());
            response.put("statuscode", 200);
            response.put("msg", "get du lieu thanh cong oh yeah da qua xa da");

            response.put("currentpage", pageNumber);
            response.put("isFirst", pageResult.isFirst());
            response.put("isLast", pageResult.isLast());
            response.put("hasNext", pageResult.hasNext());
            response.put("hasPrevious", pageResult.hasPrevious());
            response.put("totalPage", pageResult.getTotalPages());
            response.put("totalElement", pageResult.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
           response.put("data", null);
           response.put("statuscode", 404);
           response.put("msg", " la du lieu khong co hu hu hu hu");

           return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /*II - Post(create)*/


    /*III - Put(Update0*/


    /*IV- Delete(xoa)*/


}
