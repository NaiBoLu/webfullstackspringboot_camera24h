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
import webcamera.com.vn.webapp.DTO.admin.ShopProductImageDTO_AD.ShopProductImageCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopProductImageDTO_AD.ShopProductImageUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.entity.admin.ShopProductImageAD;
import webcamera.com.vn.webapp.repository.ShopProductImageRepository;

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
public class ShopProductImageServiceAD {
    @Autowired
    private ShopProductImageRepository shopProductImageRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // get all co phan trang
    public ResponseEntity<Map<String, Object>> getAllShopProductImage(Integer pageNumber, Integer pageSize, String sortby){
        //tao response luu thong tin tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageAble = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortby)); // yêu cầu
        Page<ShopProductImageAD> pageResult = shopProductImageRepo.findAll(pageAble); // kết quả

        //nêu có nội dung thì trả về ket qua ko thi thong bao loi ko tim thay
        if(pageResult.hasContent()){

            response.put("data",pageResult.getContent());
            response.put("statuscode",201);
            response.put("msg","lay du lieu thanh cong oh yeah babe");

            response.put("currentPage",pageNumber);
            response.put("isFirst",pageResult.isFirst());
            response.put("isLast",pageResult.isLast());
            response.put("hasPrevious",pageResult.hasPrevious());
            response.put("hasNext",pageResult.hasNext());
            response.put("Total element",pageResult.getTotalElements());
            response.put("Total page",pageResult.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg","Khong tim thay huhuhu");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //create moi 1 shopproductimage entity
    public ResponseEntity<Map<String, Object>> createShopProductImage(ShopProductImageCreateRequestDTO_AD objDTO, MultipartFile file){
        //taoj response luu thong tin tra ve
        Map<String, Object> response = new HashMap<>();
        //xu ly luu file hinh anh
        //tao bien randomstring de chua chuoi
        String randomString = "";

        //dung datetimeformart de dat ten anh ko trung
        DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        randomString = LocalDateTime.now().format(iso_8601_formatter);

        //lay duong dan thu muc goc toi du an tren moi he dieu hanh
        String rootFolder = Paths.get("").toAbsolutePath().toString();

        //tao ten file moi ket hop vs ten file goi len
        String newFile = randomString + "_" + file.getOriginalFilename();

        //tao duong dan toi thu muc luu file upload trong server
        String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

        //bor file vao la thu ghi dia chi filePath de multipartfile xu ly
        File destinationPath = new File(filePath);

        //taoj ra thu muc chua file neu no chua ton tai bang mkdirs()
        destinationPath.getParentFile().mkdirs();

        //chuyen objDto thanh entity
        ShopProductImageAD newEntity = new ShopProductImageAD();

        newEntity.setProductId(objDTO.getProductId());

        newEntity.setImage(newFile);

        //goij repo luu file lai
        ShopProductImageAD createEntity = shopProductImageRepo.save(newEntity);
        //dung transferTo de luu file
        try{
            file.transferTo(destinationPath);
        }catch(IOException ex){
            ex.printStackTrace();
            throw new ConstraintViolationException("co loi roi huhuhu",null);
        }

        //dung response luu ket qua tra ve
        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","tao thanh cong oh yeah");

        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    public ResponseEntity<Map<String, Object>> updateShopProductImage(Integer id, ShopProductImageUpdateRequestDTO_AD objDTO, MultipartFile file){
        Map<String, Object> response = new HashMap<>();

        //tìm kiếm entity update bang repo
        Optional<ShopProductImageAD> optFound = shopProductImageRepo.findById(id);

        //nếu tồn tại thì xử lý update
        if(optFound.isPresent()){
            //lay entity ra khoi hop qua optinal
            ShopProductImageAD entityEdit = optFound.get();

            if(objDTO.getProductId() != null){
                entityEdit.setProductId(objDTO.getProductId());
            }

            //xu lu update neu file khac null
            if(file != null){
                //b1: luu file moi vao thu muc upload
                String randomString = "";

                //dung dateTimeformatter tranh trung ten file
                DateTimeFormatter iso_8601_formattr = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                randomString = LocalDateTime.now().format(iso_8601_formattr);

                //lay thu muc root tren moi hdh
                String rootFolder = Paths.get("").toAbsolutePath().toString();

                //tao ten newFile moi bang ten cua file + datetime tranh trung
                String newFile = randomString + "_" + file.getOriginalFilename();

                //tao duong dan tuyet doi den cho chứa file ảnh
                String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

                //bỏ đường dẫn địa chỉ vào phong thư File ms dùng đc
                File destinationPath = new File(filePath);

                //dung mkdirs tạo thư mục upload neu no chua ton tai
                destinationPath.getParentFile().mkdirs();

                //luu file vào thu muc cua bang transferTo()
                try{
                    file.transferTo(destinationPath);
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                //b2: xóa file cũ đi khỏi thu muc chua
                Path delFilePath = Path.of(rootFolder + File.separator + uploadDir + File.separator + entityEdit.getImage());
                try{
                    Files.deleteIfExists(delFilePath);
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                //b3: luu ten file mới vào csdl
                entityEdit.setImage(newFile);
            }

            //luu entity vao db vua update vao csdl bang repo
            shopProductImageRepo.save(entityEdit);

            //goi response tra ket qua ve va thong bao
            response.put("data",entityEdit);
            response.put("statuscode",200);
            response.put("msg","update thanh cong oh yeah");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else{
            response.put("data", null);
            response.put("statuscode",404);
            response.put("msg","khong tim thay id huhuhu");

            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete theo id
    public ResponseEntity<Map<String, Object>> deleteShopProductImage(Integer id){
        //tao response luu kq tra ve
        Map<String, Object> response = new HashMap<>();

        //dung repo tim kiem theo id
        Optional<ShopProductImageAD> optFound = shopProductImageRepo.findById(id);

        if(optFound.isPresent()){
            //lay entity ra khoi hop qua repo
            ShopProductImageAD entityDel = optFound.get();

            //tiến hành xóa file ảnh
            String rootPath = Paths.get("").toAbsolutePath().toString();

            Path delFilePath = Path.of(rootPath + File.separator + uploadDir + File.separator + entityDel.getImage());

            try{
                Files.deleteIfExists(delFilePath);
            }catch(IOException ex){
                ex.printStackTrace();
            }

            //goi repo xoa csdl trong table
            shopProductImageRepo.delete(entityDel);

            response.put("data",null);
            response.put("statuscode",200);
            response.put("msg","xoa thanh cong oh yeah");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","tai khoang xoa ko ton tai");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }


}
