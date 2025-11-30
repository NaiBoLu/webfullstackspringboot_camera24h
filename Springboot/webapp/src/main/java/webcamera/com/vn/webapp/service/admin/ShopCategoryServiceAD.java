package webcamera.com.vn.webapp.service.admin;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.admin.ShopCategoryDTO_AD.ShopCategoryCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopCategoryDTO_AD.ShopCategoryUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.entity.admin.ShopCategoryAD;
import webcamera.com.vn.webapp.repository.ShopCategoryRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopCategoryServiceAD {
    //@Autowired gọi thủ kho repo vô để sử dụng
    @Autowired
    private ShopCategoryRepository shopCategoryRepo;

    //cau hinh file upload image
    @Value("${file.upload-dir}")
    private String uploadDir;

    /*I - GET ->lay va do du lieu co phan trang*/
    public ResponseEntity<Map<String, Object>> getAllShopCategory(int pageNumber, int pageSize, String sortby){
        //tạo response trả kết quả về kiểu thông báo lmao
        Map<String, Object> response = new HashMap<>();


        //b- yeu cau repository lay du lieu -> goi den repository goi den thao tac crud
        /*
         * Pageable: la mot giao dien trong spring data dc su dung de ho tro phan trang
         * sap xep trang
         *  + pageNUmber: trang so may(trang dang xem)
         *  + pageSize:  tong so luong trang
         *  + sortBy: sap xep trang cot nao: id or theo ten name...
         * */
        Pageable  pageable = PageRequest.of(pageNumber - 1,pageSize, Sort.by(sortby)); // yeu cau
        Page<ShopCategoryAD> pageResult = shopCategoryRepo.findAll(pageable); // keets qua

        if(pageResult.hasContent()){
            //tra ket qua cho nguoi dung -> tra theo chuan restfull APi sieu cap vip pro
            response.put("data",pageResult.getContent());
            response.put("statuscode",200);
            response.put("msg","get du lieu thanh cong oh yeah da qua xa da");

            response.put("current",pageNumber);
            response.put("isFirst",pageResult.isFirst()); // phair trang dau tien ko
            response.put("isLast",pageResult.isLast()); // trang cuoi ko
            response.put("Previous",pageResult.hasPrevious()); // trang truoc
            response.put("Next",pageResult.hasNext()); // trang ke tiep
            response.put("TotalPage",pageResult.getTotalPages());
            response.put("TotalElement",pageResult.getTotalElements());


            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data",null);
            response.put("statuscode", 404);
            response.put("msg","Khong tim thay du lieu huhuhu");

            return  new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

        }
    }



    /*II - Post(create)*/
    public ResponseEntity<Map<String, Object>> createShopCategory(ShopCategoryCreateRequestDTO_AD objCreate, MultipartFile file) {
        //a - khoi tao bien response de luu tru ket qua tra ve
        //a - khoi tao bien response de luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();


        /*******xu ly luu ruot img khi create Use******/
        //tao chuoi randomString  rong ->
        String randomString = "";

        //su dung datetime tranh trung ten file
        DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        randomString = LocalDateTime.now().format(iso_8601_formatter);
        /*thiet lap file path lay dung ten goc o dia luu folder trong project
         * => thg thiet lap file chi dinh url lay: D:\\DOWNLOAD\\img\\....
         * <=> tuy nhien, ntn vd may windown url  D:\\DOWNLOAD\\img\\.... nhung o may mac  D:/DOWNLOAD/img/....
         * nhu vay neu thiet lap code nay o tren may windown thi qua may mac doan code rootFolder nay khong sai
         * nhung ma khac he dieu hanh thi no khong hieu.. viet code nt la viet code co dinh viet code ngu
         * ==-=> lib java.nio.file.Paths;
         * */
        String rootFolder = Paths.get("").toAbsolutePath().toString();


        //tao ten file tranh trung
        String newFile = randomString + file.getOriginalFilename();
        // tạo đường dẫn tuyệt đối cho image
        String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

        //bỏ đường dẫn tuyệt đối vừa tạo vào obj phong bì FIle nó chỉ là tấm bìa ghi địa chỉ thôi!
        File destinationFile = new File(filePath);

        //mkdirs():kiem tra thu muc upload cho de chua anh co ton tai ko neu ko thi tạo
        destinationFile.getParentFile().mkdirs();

        //c-1 khoi tao shopcategoryEntity để truyền createDTO vào
        ShopCategoryAD newEntity = new ShopCategoryAD();

        newEntity.setCategoryCode(objCreate.getCategoryCode());
        newEntity.setCategoryName(objCreate.getCategoryName());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setImage(newFile);

        // c-2 yeu cau repository luu lai khoi tao tren
        // thuc hien nhan ten dk username va tien hanh kiem tra tranh trung ten username khi dang ky
        ShopCategoryAD existingCategory = shopCategoryRepo.findByCategoryName(objCreate.getCategoryName());

        if(existingCategory != null){
            //nem loi thong bao de khong cho phep tao trung ten
            throw new ConstraintViolationException("ten ban dung da ton tai vui long dat ten khac",null);
        }else{
            ShopCategoryAD createEntity = shopCategoryRepo.save(newEntity);

            //tien hanh lay ruot anh luu phong bì lúc nãy vàothuw mục
            try{
                file.transferTo(destinationFile);
            }catch(IOException ex){
                ex.printStackTrace();
            }

            //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
            response.put("data", createEntity);
            response.put("statuscode", 200);
            response.put("msg", " create thanh cong");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

//    /*III - Put(Update0*/
    public ResponseEntity<Map<String, Object>> updateShopCateogyr(Integer id, ShopCategoryUpdateRequestDTO_AD objEdit, MultipartFile file){
        //khoi tao response luu thong tin tra ve
        Map<String, Object> response = new HashMap<>();

        //nho repo tim kiem entity dua tren id muon update
        Optional<ShopCategoryAD> optFound = shopCategoryRepo.findById(id);

        //kiem tra va cap nhat cac truong tt null hoac empty -> tien hanh bo qua va ghi nhan
        if(optFound.isPresent()){
            //lay entity vua tim kiem dc ra khoi hop qua optional
            ShopCategoryAD entityEdit = optFound.get();

            //kiem tra va cap nhat cac truong tt null hoac empty thi bo qua ghi nhan cac truong khac null va empty
            if(objEdit.getCategoryCode() != null && !objEdit.getCategoryCode().isEmpty()){
                entityEdit.setCategoryCode(objEdit.getCategoryCode());
            }
            if(objEdit.getCategoryName() != null && !objEdit.getCategoryName().isEmpty()){
                entityEdit.setCategoryName(objEdit.getCategoryName());
            }
            if(objEdit.getDescription() != null && !objEdit.getDescription().isEmpty()){
                entityEdit.setDescription(objEdit.getDescription());
            }
            if(file != null){
                /*qui trinh 1- update file moi(khoi create file img moi) vao thu muc mong doi*/
                //tao chuoi randomString rong de luu gia tri bien moi vao
                String random = "";
                //tien hanh luu ten img voi dang tenanh_datetime
                DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyMMdd_HHmmss");
                random = LocalDateTime.now().format(iso_8601_formatter);
                //tao thu muc goc chua anh
                String rootFolder = Paths.get("").toAbsolutePath().toString();

                //taoj ten file anhr moi gui len cap nhat
                String newFile = random + file.getOriginalFilename();

                //tao duong dan tuyet doi tu thu muc goc den file chua anh
                String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

                //tạo lá thu File bỏ đường dẫn hình vào thư mục
                File destinationFile = new File(filePath);

                //mkdirs(): kiem tra co ton tai thu muc chua anh chua neu chua thi tạo thu muc
                destinationFile.getParentFile().mkdirs();

                //tién hành luu file bang transferTo
                try{
                    file.transferTo(destinationFile);
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                /*qui trinh 2: tien hanh xoa img cu di*/
                Path delFilePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + entityEdit.getImage());
                try{
                    Files.deleteIfExists(delFilePath);
                }catch (IOException ex){
                    ex.printStackTrace();
                }

                /*qui trinh 3: tien hanh cap nhat csdl*/
                entityEdit.setImage(newFile);
            }

            // nho repo luu lai cap nhat vao database
            shopCategoryRepo.save(entityEdit);

            //tra ve thong bao chuan restful api
            response.put("data",entityEdit);
            response.put("statuscode", 200);
            response.put("msg", "update thanh cong lmao");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data", null);
            response.put("statuscode",404);
            response.put("msg","update khong thanh cong ko tim thay id");

            return new ResponseEntity<>(response,  HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /*IV- Delete(xoa)*/
    public ResponseEntity<Map<String, Object>> delteShopcategory(Integer id){
        //a - khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repository goi method tim kiem id can xoa
        /*
         * Optional:
         *  + la mot lop trong java(java.util.Optional) dc gioi thieu tu java 8
         *  + no la mot container object hộp chứa quà co the chua mot gia tri khong null  hoac rong emtpy
         *  + muc tieu chinh Optional la giup iam thieu loi NullPointerException khi ma minhf
         * xu ly voi cac gia tri null
         * */
        Optional<ShopCategoryAD> optFound = shopCategoryRepo.findById(id);
        if(optFound.isPresent()){
            //neu ton tai id can tim thi lay no ra khỏi hộp -> ghi nhan no vao entity
            ShopCategoryAD delEntity = optFound.get();

            /*xu ly tien hanh xoa ruot anh ung voi id cua anh do*/
            String rootFolder = Paths.get("").toAbsolutePath().toString();
            Path filePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + delEntity.getImage());

            //goi thu kho repo xoa no
            shopCategoryRepo.delete(delEntity);

            //xóa file đã tồn tại
            try{
                Files.deleteIfExists(filePath);
            }catch(IOException ex){
                ex.printStackTrace();
            }

            //tra ve ket qua nguoi dung
            response.put("data",null);
            response.put("statuscode",201);
            response.put("msg","delete thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            //tra ve chuan restfull api thong bao la khong ton tai id can xoa
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","khong ton tai huhuhu");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

}
