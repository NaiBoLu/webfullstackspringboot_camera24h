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
import webcamera.com.vn.webapp.DTO.admin.ShopStoreDTO_AD.ShopStoreCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopStoreDTO_AD.ShopStoreUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.entity.admin.ShopStoreAD;
import webcamera.com.vn.webapp.repository.ShopStoreRepository;

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

@Service
public class ShopStoreServiceAD {

    // thêm thủ kho repo vào để phân trang và crud dữ liệu
    @Autowired
    private ShopStoreRepository shopStoreRepo;

    /*tao bien string lay url cau hinh luu file da thiet lap ben application.properties
     * @Value: annotation dc su dung de gan gia tri cho mot bien tu cac nguon:
     *  + application.properties/application.yaml
     *  ....
     * */
    @Value("${file.upload-dir}")
    private String uploadDir;

    /*I - GET ->lay va do du lieu co phan trang*/
    public ResponseEntity<Map<String, Object>> getAllShopStorePagination(int pageNumber, int pageSize, String sortby){
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
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortby)); // đây là miêu tả yêu cầu
        Page<ShopStoreAD> pageResult = shopStoreRepo.findAll(pageable); // trả về kết quả page phân trang theo yêu cầu ở trên
        if(pageResult.hasContent()){ // nếu pageresult có nội dung
            //tra ket qua cho nguoi dung -> tra theo chuan restfull APi sieu cap vip pro
            response.put("data",pageResult.getContent());
            response.put("statuscode",201);
            response.put("msg","lay du lieu thanh cong oh yeah babe!!");

            response.put("currentpage",pageNumber); // trang hiện tại
            response.put("isFirst",pageResult.isFirst()); // trang đầu tiên
            response.put("isLast",pageResult.isLast()); // hiện trang cuối cùng
            response.put("hasNext",pageResult.hasNext()); // trang tiếp theo
            response.put("hasPrevious",pageResult.hasPrevious()); // trang trước đó
            response.put("totalPage",pageResult.getTotalPages()); // tất cả các trang
            response.put("totalElement",pageResult.getTotalElements()); // lol

            return new ResponseEntity<>(response,HttpStatus.OK); // trả về entity và thông báo ở trên cho client

        }else {
            // trả về lỗi ko tìm thấy trang nào
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg","Khong co du lieu lmao");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    /*II - Post(create) tạo thêm 1 shopstores mới*/
    public ResponseEntity<Map<String, Object>> createShopStore(ShopStoreCreateRequestDTO_AD objCreate, MultipartFile file) {
        //a - khoi tao bien response de luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        /*******xu ly luu ruot img khi create Use******/
        //tao chuoi randomString  rong ->
        String randomString="";

        //su dung datetime luu thong tin anh tranh trung ten va thoi gian luu anh
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


        /*tao duong dan xu ly luu file
         *  + file.getOriginalFilename(); method xu ly ghi nhan lay cai file ruot anh va tien hanh ghi nhan va luu vao trong folder uploads
         *  + file.separator: co nhiem vu chinh la dung de chi dau phan cach thu muc: // cua windown, hay dau \ cua mac
         *  + uploadDir: chinh la ten file lien ket voi cau hinh properties ben file application.properties ban nay
         * */

        String newFile = randomString + "_" + file.getOriginalFilename();
        String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;


        //tien hanh xu ly luu file vao thu muc uploads. Đây là hành động lấy ra một chiếc phong bì mới tinh và viết Địa chỉ Nhà (filePath)
        // lên đó. Chiếc phong bì này chưa chứa bức thư hay ảnh đâu nhé, nó chỉ là tấm bìa ghi địa chỉ thôi!
        File destinationFile = new File(filePath);

        //mkdirs():kiem tra thu muc upload cho de chua anh co ton tai ko neu ko thi tạo
        destinationFile.getParentFile().mkdirs();

        //c-1 khoi tao ShopStoreEntity truyền requestDTO vào
        ShopStoreAD newEntity = new ShopStoreAD();

        newEntity.setStoreCode(objCreate.getStoreCode());
        newEntity.setStoreName(objCreate.getStoreName());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setImage(newFile);

        // c-2 yeu cau repository luu lai khoi tao tren
        // thuc hien nhan ten dk username va tien hanh kiem tra tranh trung ten username khi dang ky
        ShopStoreAD existingShop = shopStoreRepo.findByStoreName(objCreate.getStoreName());

        // c-3 thuc hien kiem tra ds data trong mysql co trung ten username nao khong
        if(existingShop != null){
            //nem loi thong bao de khong cho phep tao trung ten
            throw new ConstraintViolationException("Ten ban dung da ton tai vui long chon ten khac ", null);
        }else{
            ShopStoreAD createEntity = shopStoreRepo.save(newEntity);

            //tien hanh lay ruot anh(anh goc, kich co anh(nhieu mb...)) ghi nhan va luu vao file
            try{
                file.transferTo(destinationFile);
            }catch(IOException e){
                e.printStackTrace();
            }

            //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
            response.put("data", createEntity);
            response.put("statuscode", 200);
            response.put("msg", " create thanh cong");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /*III - Put(Update0*/
    public ResponseEntity<Map<String, Object>> updateShopStore(Integer id, ShopStoreUpdateRequestDTO_AD objEdit, MultipartFile file){
        // khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repo tim kiem entity(dua tren id) ma muon update
        Optional<ShopStoreAD> optFound = shopStoreRepo.findById(id);

        //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
        if(optFound.isPresent()){
            //nhan dc id vua tim kiem va gan vao entity cua uder de doi chung lấy ra khỏi hộp quà
            ShopStoreAD entityEdit = optFound.get();

            //kiem tra va cap nhat cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan nếu trống thì bỏ qua
            if(objEdit.getStoreCode() != null && !objEdit.getStoreCode().isEmpty()){
                entityEdit.setStoreCode(objEdit.getStoreCode());
            }
            if(objEdit.getStoreName() != null && !objEdit.getStoreName().isEmpty()){
                entityEdit.setStoreName(objEdit.getStoreName());
            }
            if(objEdit.getDescription() != null && !objEdit.getDescription().isEmpty()){
                entityEdit.setDescription(objEdit.getDescription());
            }
            if(file != null){
                /*qui trinh 1- update file moi(khoi create file img moi) vao thu muc mong doi*/
                //tao chuoi randomString rong de luu gia tri bien moi vao
                String randomString = "";
                //tien hanh luu ten img voi dang tenanh_datetime
                DateTimeFormatter iso_fommater = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                randomString = LocalDateTime.now().format(iso_fommater);

                //tạo muc lay folder goc chua anh
                String rootFolder = Paths.get("").toAbsolutePath().toString();

                //tạo tên file ảnh mới gửi lên cập nhật
                String newFile = randomString + "_" + file.getOriginalFilename();

                //tao đường dẫn tuyệt đối từ thư mục gốc tới chỗ lưu ảnh mới
                String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

                //tiến hành tạo lá thư File bỏ đường dẫn hình vào thư mục
                File destinationFile = new File(filePath);

                //mkdirs: kiem tra coi co ton tai folder upload chưa nếu chưa có thì tạo ra chỗ chứa ảnh
                destinationFile.getParentFile().mkdirs();

                //tiêns hành lưu file bằng transferTo kiểm tra try catch nếu file có lỗi
                try{
                    file.transferTo(destinationFile);
                }catch(IOException e){
                    e.printStackTrace();
                }

                /*qui trinh 2: tien hanh xoa img cu di*/
                Path delFilePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + entityEdit.getImage());
                try{
                    Files.deleteIfExists(delFilePath);
                }catch(IOException e){
                    e.printStackTrace();
                }

                /*qui trinh 3: tien hanh cap nhat csdl*/
                entityEdit.setImage(newFile);
            }

            //nho repo luu lại
            shopStoreRepo.save(entityEdit);


            //tra ve thogn bao chuan restfull api
            response.put("data", entityEdit);
            response.put("statuscode", 200);
            response.put("msg", "update thanh cong roi yeah yeah");

            return new ResponseEntity<>(response , HttpStatus.OK);

        }else{
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg", " update khong thanh cong");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*IV- Delete(xoa)*/
    public ResponseEntity<Map<String, Object>> deleteShopStore(Integer id){
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
        Optional<ShopStoreAD> optFound = shopStoreRepo.findById(id);
        if(optFound.isPresent()){
            //neu ton tai id can tim thi lay no ra khỏi hộp -> ghi nhan no vao entity
            ShopStoreAD delEntity = optFound.get();

            /*xu ly tien hanh xoa ruot anh ung voi id cua anh do*/
            String rootFolder = Paths.get("").toAbsolutePath().toString();
            Path filePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + delEntity.getImage());

            //gọi repo thủ kho xóa
            shopStoreRepo.delete(delEntity);

            try{
                // tiến hành xóa file đã tồn tại
                Files.deleteIfExists(filePath);
            }catch(IOException e){
                e.printStackTrace();
            }

            //tra ve ket qua nguioi dung chuan restfull api
            response.put("data",null);
            response.put("statuscode",200);
            response.put("msg","delete thanh cong lol");

            return new ResponseEntity<>(response, HttpStatus.OK);

        }else{
            //tra ve chuan restfull api thong bao la khong ton tai id can xoa
            response.put("data", null);
            response.put("statuscode",404);
            response.put("msg","khong ton tại huhuhu");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



}
