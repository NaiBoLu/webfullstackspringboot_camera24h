package webcamera.com.vn.webapp.service;


import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.UserDTO.UserCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.UserDTO.UserUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.User;
import webcamera.com.vn.webapp.exceptions.ValidationErrorResponse;
import webcamera.com.vn.webapp.exceptions.Violations;
import webcamera.com.vn.webapp.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*lop luan ly logic code*/

@Service
public class UserService {

    @Autowired
    private webcamera.com.vn.webapp.repository.UserRepository userRepo;

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
    public ResponseEntity<Map<String, Object>> createUser(UserCreateRequestDTO objCreate){
        //a - khoi tao bien response de luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //b-1 xu ly service  validation exception kiem tra tinh hop le khi dien thong tin
        ValidationErrorResponse responseError = new ValidationErrorResponse();
        if(objCreate.getUsername().equalsIgnoreCase("Admin") || objCreate.getUsername().equalsIgnoreCase("quan tri vien")){
            responseError.getViolations().add(new Violations("username", "khong duoc dung ten nay de dk tai khoan user"));
        }
        //b-2 xu ly password
        /*
      + phân tích:
            ^: Bắt đầu chuỗi.
            [a-zA-Z0-9._%+-]+: Một hoặc nhiều ký tự chữ, số, hoặc ký tự đặc biệt (., _, %, +, -).
            @: Ký tự bắt buộc.
            [a-zA-Z0-9.-]+: Một hoặc nhiều ký tự trong tên miền.
            .: Dấu chấm (thoát với \ vì . là ký tự đặc biệt).
            [a-zA-Z]{2,}: Tên miền có ít nhất 2 ký tự.
            $: Kết thúc chuỗi.*/
        String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        /*
        +Pattern.compile(regExpn: Tạo ra một đối tượng Pattern. Đây là bước biên dịch (compile)
        biểu thức Regex (chuỗi regExpn) thành một đối tượng có thể sử dụng để so khớp hiệu quả.
        + Pattern.CASE_INSENSITIVE: Dây là một cờ (flag) tùy chọn. Nó cho phép việc so khớp không
         phân biệt chữ hoa/chữ thường.
        + Matcher matcher = pattern.matcher(objCreate.getPassword());: Chuỗi đầu vào cần kiểm tra
        (mật khẩu mà người dùng vừa nhập).
        * */
        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(objCreate.getPassword());

        // tao bien kiem tra mk co du manh regex chua
        boolean isPasswordIstrong = matcher.matches();
        if(isPasswordIstrong == false){
            responseError.getViolations().add(new Violations("password","mat khau ban tao phai co ky tu in hoa, in thuong, va it nhat mot ky tu dac biet"));
        }

        //c - kiem tra neu nguoi dung khong vi pham bat ke service validation nao thi cho luu
        if(responseError.getViolations().size() == 0){
            //c-1 khoi tao UserEntity
            User newEntity = new User();
            newEntity.setUsername(objCreate.getUsername());
            newEntity.setPassword(objCreate.getPassword());
            newEntity.setLastName(objCreate.getLastName());
            newEntity.setFirstName(objCreate.getFirstName());
            newEntity.setEmail(objCreate.getEmail());
            newEntity.setPhone(objCreate.getPhone());
            newEntity.setStatus((long)1);//set mac dinh avatar mac dinh

           // c-2 yeu cau repository luu lai khoi tao tren
            // thuc hien nhan ten dk username va tien hanh kiem tra tranh trung ten username khi dang ky
           User existingUser = userRepo.findByUsername(objCreate.getUsername());

           // c-3 thuc hien kiem tra ds data trong mysql co trung ten username nao khong
            if(existingUser != null){
                //nem loi thong bao de khong cho phep tao trung ten
                throw new ConstraintViolationException("Ten ban dang ky da ton tai vui long chon ten khac hahaaha", null);
            }else{
                User createEntity = userRepo.save(newEntity);

                //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
                response.put("data", createEntity);
                response.put("statuscode", 200);
                response.put("msg", " create thanh cong");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }else{
            response.put("data", responseError);
            response.put("statuscode", 501);
            response.put("msg", " du lieu chua dat yeu cau can xem lai");

            return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
        }



    }


    /*III - Put(Update0*/
    public ResponseEntity<Map<String, Object>> updateUser(Integer id, UserUpdateRequestDTO objEdit){
        // khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repo tim kiem entity(dua tren id) ma muon update
        Optional<User> optFound = userRepo.findById(id);
        if(optFound.isPresent()){
            //nhan dc id vua tim kiem va gan vao entity cua uder de doi chung
            User entityEdit = optFound.get();

            //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
            if(objEdit.getPassword() != null && !objEdit.getPassword().isEmpty()){
                entityEdit.setPassword(objEdit.getPassword());
            }
            if(objEdit.getLastName() != null && !objEdit.getLastName().isBlank()){
                entityEdit.setLastName(objEdit.getLastName());
            }
            if(objEdit.getFirstName() != null && !objEdit.getFirstName().isEmpty()){
                entityEdit.setFirstName(objEdit.getFirstName());
            }
            if(objEdit.getEmail() != null && !objEdit.getEmail().isEmpty()){
                entityEdit.setEmail(objEdit.getEmail());
            }
            if(objEdit.getPhone() != null && !objEdit.getPhone().isEmpty()){
                entityEdit.setPhone(objEdit.getPhone());
            }
            if(objEdit.getAddress() != null && !objEdit.getAddress().isEmpty()){
                entityEdit.setAddress(objEdit.getAddress());
            }
            if(objEdit.getStatus() != null){
                entityEdit.setStatus(objEdit.getStatus());
            }

            //nhow repository update
            userRepo.save(entityEdit);

            //tra ve thogn bao chuan restfull api
            response.put("data", entityEdit);
            response.put("statuscode", 200);
            response.put("msg", "update thanh cong roi yeah yeah");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg", " update khong thanh cong");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    /*IV- Delete(xoa)*/
    public ResponseEntity<Map<String, Object>> deleteUsre(Integer id){
        //a - khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repository goi method tim kiem id can xoa
        /*
        * Optional:
        *  + la mot lop trong java(java.util.Optional) dc gioi thieu tu java 8
        *  + no la mot container object co the chua mot gia tri khong null  hoac rong emtpy
        *  + muc tieu chinh Optional la giup iam thieu loi NullPointerException khi ma minhf
        * xu ly voi cac gia tri null
        * */
        Optional<User> optFound = userRepo.findById(id);
        if(optFound.isPresent()){
            //neu ton tai id can tim thi lay no ra -> ghi nhan no vao entity
            User delEntity = optFound.get();
            //nho repository xoa
            userRepo.delete((delEntity));

            //tra ve ket qua nguioi dung chuan restfull api
            response.put("data", null);
            response.put("statuscode", 200);
            response.put("msg", "delete thanh cong oh yeah yeah");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            //tra ve chuan restfull api thong bao la khong ton tai id can xoa
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg", "tai khoan xoa khong ton tai");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
