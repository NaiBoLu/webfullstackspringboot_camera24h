package webcamera.com.vn.webapp.service;

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
import webcamera.com.vn.webapp.DTO.ShopSupplierDTO.ShopSupplierCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopSupplierDTO.ShopSupplierUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopSupplier;
import webcamera.com.vn.webapp.repository.ShopSupplierRepository;

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
public class ShopSupplierService {
    //goi thủ kho repo sử dụng crud và phan trang
    @Autowired
    private ShopSupplierRepository shopSupplierRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //lay tat ca co phan trang
    public ResponseEntity<Map<String, Object>> getAllShopSupplier(Integer pageNumber, Integer pageSize, String sortby){
        // tạo response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageAble = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortby)); // yêu cầu phân trang
        Page<ShopSupplier> pageResult = shopSupplierRepo.findAll(pageAble); // ket quả

        //tra ve ket qua neu page co noi dung ben trong
        if(pageResult.hasContent()){
            //tra ket qua cho nguoi dung -> tra theo chuan restfull APi sieu cap vip pro
            response.put("data",pageResult.getContent());
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
    public ResponseEntity<Map<String, Object>> createShopSuplier(ShopSupplierCreateRequestDTO objCreate, MultipartFile file){
        //tao repo luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        /*******xu ly luu ruot img khi create Use******/
        //tao chuoi randomString  rong ->
        String random = "";

        //su dung datetime luu thong tin anh tranh trung ten va thoi gian luu anh
        DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        random = LocalDateTime.now().format(iso_8601_formatter);

        /*thiet lap file path lay dung ten goc o dia luu folder trong project
         * => thg thiet lap file chi dinh url lay: D:\\DOWNLOAD\\img\\....
         * <=> tuy nhien, ntn vd may windown url  D:\\DOWNLOAD\\img\\.... nhung o may mac  D:/DOWNLOAD/img/....
         * nhu vay neu thiet lap code nay o tren may windown thi qua may mac doan code rootFolder nay khong sai
         * nhung ma khac he dieu hanh thi no khong hieu.. viet code nt la viet code co dinh viet code ngu
         * ==-=> lib java.nio.file.Paths;
         * */
        String rootFolder = Paths.get("").toAbsolutePath().toString();

        String newFile = random + file.getOriginalFilename();
        String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

        //tien hanh xu ly luu file vao thu muc uploads. Đây là hành động lấy ra một chiếc phong bì mới tinh và viết Địa chỉ Nhà (filePath)
        // lên đó. Chiếc phong bì này chưa chứa bức thư hay ảnh đâu nhé, nó chỉ là tấm bìa ghi địa chỉ thôi!
        File destinationFile = new File(filePath);

        //mkdirs():kiem tra thu muc upload cho de chua anh co ton tai ko neu ko thi tạo

        destinationFile.getParentFile().mkdirs();

        //khoi tao entity truyen dto vao
        ShopSupplier newEntity = new ShopSupplier();

        newEntity.setSupplierCode(objCreate.getSupplierCode());
        newEntity.setSupplierName(objCreate.getSupplierName());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setImage(newFile);

        //kiem tra ten supplier co ton tai hay chua

        ShopSupplier existingEntity = shopSupplierRepo.findBySupplierName(objCreate.getSupplierName());

        if(existingEntity != null){
            throw new ConstraintViolationException("Ten ban dung da ton tai vui long dat ten khac",null);
        }else{
            //luu entity vao database bang repo
            ShopSupplier createEntity = shopSupplierRepo.save(newEntity);

            //luu file vao thu muc upload tren server
            try{
                file.transferTo(destinationFile);
            }catch(IOException ex){
                ex.printStackTrace();
            }

            //b-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
            response.put("data", createEntity);
            response.put("statuscode", 200);
            response.put("msg", " create thanh cong");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /*III - Put(Update0*/
    public ResponseEntity<Map<String, Object>> updateShopSupplier(Integer id, ShopSupplierUpdateRequestDTO objEdit, MultipartFile file){
        //tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //tim kiem entity theo id coi co ton tai ko
        Optional<ShopSupplier> optFound = shopSupplierRepo.findById(id);

        //neu ton tai thi tien hanh chuyen dto vao entity
        if(optFound.isPresent()){
            //lay entity ra khoi cai hop optinal
            ShopSupplier entityEdit = optFound.get();

            //kiem tra va cap nhat cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan nếu trống thì bỏ qua
            if(objEdit.getSupplierCode() != null && !objEdit.getSupplierCode().isEmpty()){
                entityEdit.setSupplierCode(objEdit.getSupplierCode());
            }
            if(objEdit.getSupplierName() != null && !objEdit.getSupplierName().isEmpty()){
                entityEdit.setSupplierName(objEdit.getSupplierName());
            }
            if(objEdit.getDescription() != null && !objEdit.getDescription().isEmpty()){
                entityEdit.setDescription(objEdit.getDescription());
            }
            // xử lý update file ảnh mới
            if(file != null){
                //tạo chuỗi random string ->
                String randomString = "";
                //dung datetimformatt de dat ten file ko bi trung
                DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                randomString = LocalDateTime.now().format(iso_8601_formatter);

                //lay duong dan goc cua thu muc tren moi he dieu hanh
                String rootFolder = Paths.get("").toAbsolutePath().toString();

                //tạo tên file mới cho ảnh ko trùng
                String newFile = randomString + "_" + file.getOriginalFilename();

                // tạo đường dẫn từ thư mục gốc tới chỗ upload lưu file trong server
                String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

                //bỏ đường dẫn tuyệt đối file vào lá thư FIle để multifilepart nhận đc
                File destinationFile = new File(filePath);

                //mkdirs: tạo thư mục chứa nếu nó chưa tồn tại đẩm bảo có chỗ chứa
                destinationFile.getParentFile().mkdirs();

                //lưu file lại bằng transferTo ỏ vô try catch để bắt lỗi
                try {
                    file.transferTo(destinationFile);
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                //xóa file cũ đi
                Path delFilePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + entityEdit.getImage());
                //xóa file nếu nó tốnf tại
                try {
                    Files.deleteIfExists(delFilePath);
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                //luu ten file mới vào dữ liệu trong db
                entityEdit.setImage(newFile);

            }

            //luu entity vua cap nhat vao db
            shopSupplierRepo.save(entityEdit);

            response.put("data",entityEdit);
            response.put("statuscode",200);
            response.put("msg","update thành công oh yeah");

            return new ResponseEntity<>(response, HttpStatus.OK);


        }else{
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","update ko thanh cong ko tim thay");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*IV- Delete(xoa)*/
    public ResponseEntity<Map<String, Object>> deleteShopSupplier(Integer id){
        //khoi tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //tim theo id coi co entity can xoa ko
        Optional<ShopSupplier> optFound = shopSupplierRepo.findById(id);

        //nếu tồn tại thì xóa id
        if(optFound.isPresent()){
            //lấy entity ra khỏi hôp quà optional
            ShopSupplier delEntity = optFound.get();

            //tiến hành xóa file ảnh trong upload
            String rootFolder = Paths.get("").toAbsolutePath().toString();

            Path delFilePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + delEntity.getImage());

            //gọi repo xóa entity
            shopSupplierRepo.delete(delEntity);

            try{
                Files.deleteIfExists(delFilePath);
            }catch(IOException ex){
                ex.printStackTrace();
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
