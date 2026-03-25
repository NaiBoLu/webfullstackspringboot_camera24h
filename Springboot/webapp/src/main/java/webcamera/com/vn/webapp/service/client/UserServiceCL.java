package webcamera.com.vn.webapp.service.client;


import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import webcamera.com.vn.webapp.DTO.admin.UserDTO_AD.UserUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.client.UserDTO_CL.UserCreateRequestDTO_CL;
import webcamera.com.vn.webapp.DTO.client.UserDTO_CL.UserUpdateRequestDTO_CL;
import webcamera.com.vn.webapp.entity.Role;
import webcamera.com.vn.webapp.entity.User;
import webcamera.com.vn.webapp.entity.UserHasRoles;
import webcamera.com.vn.webapp.exceptions.ValidationErrorResponse;
import webcamera.com.vn.webapp.exceptions.Violations;
import webcamera.com.vn.webapp.repository.RoleRepository;
import webcamera.com.vn.webapp.repository.UserHasRolesRepository;
import webcamera.com.vn.webapp.service.aouth.EmailService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*lop luan ly logic code*/

@Service
public class UserServiceCL {

    @Autowired
    private webcamera.com.vn.webapp.repository.UserRepository userRepo;
    @Autowired
    private UserHasRolesRepository userHasRoleRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private EmailService emailService;


    /*tao bien string lay url cau hinh luu file da thiet lap ben application.properties
    * @Value: annotation dc su dung de gan gia tri cho mot bien tu cac nguon:
    *  + application.properties/application.yaml
    *  ....
    * */
    @Value("${file.upload-dir}")
    private String uploadDir;



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
            response.put("statuscode", 201);
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
    //MultipartFile: la mot interface trong spring, dc su dung de xu ly cac tep files -> dc upload thog qua giao thuc HTTP request 
    //Nếu bạn không dùng @Transactional: Chẳng may bạn tạo xong User rồi, nhưng đến đoạn tìm Role ID 3 bị lỗi (hoặc quên chưa lưu), hệ thống sẽ có một "User ma" — tức là có tài khoản nhưng không có quyền gì cả, dẫn đến lỗi khi đăng nhập sau này.
    //Nếu bạn có @Transactional: Nếu việc lưu Role bị lỗi, Spring sẽ tự động "xóa" luôn thằng User vừa tạo trước đó để đảm bảo dữ liệu trong Database luôn sạch sẽ, đúng cặp đúng cặp.
    @Transactional 
    public ResponseEntity<Map<String, Object>> createUser(UserCreateRequestDTO_CL objCreate){
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
         

            //xử ly mahoa matkhau theo chuan bcrypt thanh ma bam (hash) bao mat thong tin
            BCryptPasswordEncoder endCoder = new BCryptPasswordEncoder(); //xu ly mat 
            newEntity.setPassword(endCoder.encode(objCreate.getPassword()));

            newEntity.setGender(1);
            newEntity.setEmail(objCreate.getEmail());
            //lk khoa ngoai cua table salary_level
            newEntity.setLevelId(7);
            newEntity.setIsActive(0);

            /*tao khoa bi mat de lam ma active code -> khi x/n gmail  */
            UUID uuid= UUID.randomUUID();
            newEntity.setActiveCode(uuid.toString());

           // c-2 yeu cau repository luu lai khoi tao tren
            // thuc hien
            // nhan ten dk username va tien hanh kiem tra tranh trung ten username khi dang ky
           User existingUser = userRepo.findByUsername(objCreate.getUsername());

           // c-3 thuc hien kiem tra ds data trong mysql co trung ten username nao khong
            if(existingUser != null){
                //nem loi thong bao de khong cho phep tao trung ten
                throw new ConstraintViolationException("Ten ban dang ky da ton tai vui long chon ten khac hahaaha", null);
            }else{
                User createEntity = userRepo.save(newEntity);

                //tạo role mặc định cho user phải lưu lại ms có id
                UserHasRoles newRole = new UserHasRoles();
                 //lấy role từ db
                Role defaultRole = roleRepo.findById(3).orElseThrow(() -> new RuntimeException("Role không tồn tại lol"));

                newRole.setUser(createEntity);
                newRole.setRole(defaultRole);

                //lưu lại vào userhasrole
                userHasRoleRepo.save(newRole);

                /*****start - xu ly xac nhan email khi dang ky crate tai khoan moi*****/ 
                String to = createEntity.getEmail();
                String subject = "Verity registered account";
                String content = "Hello, " + createEntity.getUsername()
                        + "Please verify your newly created account by clicking the following confirmation link to activate your account: "
                        +"<a href=\"http://localhost:8080/api/client/users/active-account?email="
                        + createEntity.getEmail()
                        + "&activeCode=" + createEntity.getActiveCode()
                        + "\">Active Account</a>";
                 //tien hanh gui email voi cac tham so khai bao de xac nhan email
                 emailService.sendEmail(to, subject, content);       
                /*****end - xu ly xac nhan email khi dang ky crate tai khoan moi*****/

                //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
                response.put("data", createEntity);
                response.put("statuscode", 200);
                response.put("msg", " create thanh cong");

                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }else{
            response.put("data", responseError);
            response.put("statuscode", 501);
            response.put("msg", " du lieu chua dat yeu cau can xem lai");

            return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
        }

    }

    /***method xu ly kich hoat tai khoan khi dk co xac nhan gmail*****/
    public ResponseEntity<Map<String, Object>> activeAccount(String email, String activeCode){
        //khoi tap bien reponse luu ket qua  tra ve
        Map<String, Object> response = new HashMap<>();


        // nho repo tim user vaf activecode thogn qua method ben repository 
        Optional<User> optFound = userRepo.findByEmailAndActiveCode(email, activeCode);
        if(optFound.isPresent()){
            //neu tim dc thi kich hoat is_active cua user trong csdl tu 0 thanh 1
            User entityActive = optFound.get();
            entityActive.setIsActive(1);

            //tien hanh xoa bo active_code di khong can thiet nua
            entityActive.setActiveCode(null);

            //save lai trang thai vua thay doi
            userRepo.save(entityActive);

            //tra ve ket qua chuan restfull
            response.put("data", entityActive);
            response.put("statuscode", 200);
            response.put("msg", " active account thanh cong my man oh yeah");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        }else{
            response.put("data", null);
            response.put("statuscode", 501);
            response.put("msg", " tai khoan chua dc kich hoat vui long xem lai");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }


     /*III - Put(Update0*/

    public ResponseEntity<Map<String, Object>> updateUser(Integer id, UserUpdateRequestDTO_CL objEdit, MultipartFile file){
        // khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repo tim kiem entity(dua tren id) ma muon update
        Optional<User> optFound = userRepo.findById(id);
        if(optFound.isPresent()){
            //nhan dc id vua tim kiem va gan vao entity cua uder de doi chung
            User entityEdit = optFound.get();

            if(objEdit.getName() != null && !objEdit.getName().isEmpty()){
                entityEdit.setName(objEdit.getName());
            }

            if(objEdit.getUsername() != null && !objEdit.getUsername().isEmpty()){
                entityEdit.setUsername(objEdit.getUsername());
            }

            //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
            if(objEdit.getPassword() != null && !objEdit.getPassword().isEmpty()){
                //update thì vẫn phải mã hóa lại bcrypt cho pasword mới
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                entityEdit.setPassword(encoder.encode(objEdit.getPassword()));
            }


            if(objEdit.getGender() != null ){
                entityEdit.setGender(objEdit.getGender());
            } 

            if(objEdit.getEmail() != null && !objEdit.getEmail().isEmpty()){
                entityEdit.setEmail(objEdit.getEmail());
            }

            if(objEdit.getBirthday() != null ){
                entityEdit.setBirthday(objEdit.getBirthday());
            }

            /*tien hanh xu ly update img co ruot anh*/
            if(file != null){
                /*qui trinh 1- update file moi(khoi create file img moi) vao thu muc mong doi*/
                //tao chuoi randomString rong de luu gia tri bien moi vao
                String randomString = "";

                //tien hanh luu ten img voi dang tenanh_datetime
                DateTimeFormatter iso_fommater = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                randomString = LocalDateTime.now().format(iso_fommater);

                //tao muc lay folder goc chua anh
                String rootFolger = Paths.get("").toAbsolutePath().toString();

                //tao newFile chua anh moi(ruot anh moi)
                String newFile = randomString + "_" + file.getOriginalFilename();

                /*tao url dong luu anh */
                String filePath = rootFolger + File.separator + uploadDir + File.separator + newFile;

                //tien hanh xu ly luu file vao thu muc
                File destinationFile = new File(filePath);

                //mkdir: kiem tra coi co ton tai folder ten la uploads chua anh khong neu khong mkdir no tao lai
                destinationFile.getParentFile().mkdirs();

                //tien hanh lay ruot anh(anh goc) ghi nhan va luu img vao folder uploads
                try{
                    //transferTo: giup ghi nhan va lay img(ruot anh)
                    file.transferTo(destinationFile);
                }catch (IOException e){
                    e.printStackTrace();
                }

                /*qui trinh 2: tien hanh xoa img cu di*/
                Path delFilePath = Path.of(rootFolger + File.separator + uploadDir + File.separator + entityEdit.getAvatar());
                try{
                    //tien hanh delete file img da ton tai(img cu)
                    Files.deleteIfExists((delFilePath));
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                /*qui trinh 3: tien hanh cap nhat csdl*/
                entityEdit.setAvatar(newFile);
            }

            if(objEdit.getPhone() != null && !objEdit.getPhone().isEmpty()){
                entityEdit.setPhone(objEdit.getPhone());
            }
            if(objEdit.getAddress() != null && !objEdit.getAddress().isEmpty()){
                entityEdit.setAddress(objEdit.getAddress());
            }
            if(objEdit.getCountry() != null && !objEdit.getCountry().isEmpty()){
                entityEdit.setCountry(objEdit.getCountry());
            }
            if(objEdit.getRememberToken() != null && !objEdit.getRememberToken().isEmpty()){
                entityEdit.setRememberToken(objEdit.getRememberToken());
            }
            if(objEdit.getIsActive() != null){
                entityEdit.setIsActive(objEdit.getIsActive());
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

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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

            /*xu ly tien hanh xoa ruot anh ung voi taikhoan cua anh do*/
            String rootFolder = Paths.get("").toAbsolutePath().toString();
            Path filePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + delEntity.getAvatar());

            try{
                //tien hanh deleteIfExits co ton tai no moi xoa
                Files.deleteIfExists(filePath);
            }catch (IOException e){
                e.printStackTrace();
            }

            //nho repository xoa dat r
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
