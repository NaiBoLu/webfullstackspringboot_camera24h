package webcamera.com.vn.webapp.service;

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
import webcamera.com.vn.webapp.DTO.ProductDTO.ShopProductCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ProductDTO.ShopProductUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopExport;
import webcamera.com.vn.webapp.entity.ShopProduct;
import webcamera.com.vn.webapp.repository.ShopProductRepository;

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
public class ShopProductService {
    //them thu kho repo vao de su dung
    @Autowired
    private ShopProductRepository shopProductRepository;

    //them cau hinh file upload o day
    @Value("${file.upload-dir}")
    private String uploadDir;

    //get all co phan trang
    public ResponseEntity<Map<String, Object>> getAllProduct(Integer pageNumber, Integer pageSize, String sortby) {
        //tao response luu ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly lay du lieu co phan trang
        Pageable pageable = PageRequest.of(pageNumber - 1,pageSize, Sort.by(sortby) ); // yeu cau phan trang
        Page<ShopProduct> pageResult = shopProductRepository.findAll(pageable); // ket qua phan trang

        if(pageResult.hasContent()){
            //dung response hien thi ket qua
            response.put("data", pageResult.getContent());
            response.put("statuscode", 200);
            response.put("msg", "Lấy danh sách sản phẩm thành công");

            response.put("current page",pageNumber);
            response.put("isFirst", pageResult.isFirst());
            response.put("isLast", pageResult.isLast());
            response.put("hasNext", pageResult.hasNext());
            response.put("hasPrevious",pageResult.hasPrevious());
            response.put("totalItems", pageResult.getTotalElements());
            response.put("totalPages", pageResult.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg","Khong tim thay san pham huhuhu");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //create product mới
    public ResponseEntity<Map<String, Object>> createProduct (ShopProductCreateRequestDTO objCreate, MultipartFile file) {
        //tạo response lưu kết quar tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly luu file ảnh lên server
        String randomString = "";

        //datetimeformatter để ko bị trùng tên
        DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        randomString = LocalDateTime.now().format(iso_8601_formatter);

        //lấy file root đường dẫn tuyệt đối
        String rootFolder = Paths.get("").toAbsolutePath().toString();

        //tên file mới ko trùng
        String newFile = randomString + "_" + file.getOriginalFilename();

        //tạo đường dẫn tuyệt đối lưu file ảnh
        String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

        //tạo phong bì File bò địa chỉ tuyệt đối vào ms lưu đc
        File destinationFile = new File(filePath);

        //mkdirs() đảm bảo tất cả các thư mục cha được tạo nếu chưa tồn tại
        destinationFile.getParentFile().mkdirs();

        //tạo đối tượng entity để lưu vào db
        ShopProduct newEntity = new ShopProduct();

        newEntity.setProductCode(objCreate.getProductCode());
        newEntity.setProductName(objCreate.getProductName());
        newEntity.setImage(newFile);
        newEntity.setShortDescription(objCreate.getShortDescription());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setStandardCost(objCreate.getStandardCost());
        newEntity.setListPrice(objCreate.getListPrice());
        newEntity.setQuantityPerUnit(objCreate.getQuantityPerUnit());
        newEntity.setDiscontinued(objCreate.getDiscontinued());
        newEntity.setIsFeatured(objCreate.getIsFeatured());
        newEntity.setIsNew(objCreate.getIsNew());
        newEntity.setCategoryId(objCreate.getCategoryId());
        newEntity.setSupplierId(objCreate.getSupplierId());

        //lưu vào db bằng repo thủ kho
        ShopProduct savedEntity = shopProductRepository.save(newEntity);

        //luu file sau khi luu db
        //try catch bắt lỗi nếu file ko lưu đc hoặc ổ đĩa đầy
        try{
            file.transferTo(destinationFile);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        //trả về response
        response.put("data", savedEntity);
        response.put("statuscode", 201);
        response.put("msg","Tạo mới sản phẩm thành công");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //update product
    public ResponseEntity<Map<String, Object>> updateProduct(Integer id, ShopProductUpdateRequestDTO objUpdate, MultipartFile file) {
        //tạo response lưu kết quả trả về
        Map<String, Object> response = new HashMap<>();

        //kêu thủ kho repo tìm sp theo id
        Optional<ShopProduct> optFound = shopProductRepository.findById(id);

        //kiểm tra sp có tồn tại ko
        if (optFound.isPresent()) {
            //lấy quà ra khỏi hộp optinal
            ShopProduct entityEdit = optFound.get();

            //cập nhật các thuộc tính mới
            if (objUpdate.getProductCode() != null && !objUpdate.getProductCode().isEmpty()) {
                entityEdit.setProductCode(objUpdate.getProductCode());
            }
            if (objUpdate.getProductName() != null && !objUpdate.getProductName().isEmpty()) {
                entityEdit.setProductName(objUpdate.getProductName());
            }

            // xử lý update file ảnh mới
            if (file != null) {
                //xử lý update file ảnh mới
                String randomString = "";

                //datetimeformatter để ko bị trùng tên
                DateTimeFormatter iso_8601_formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                randomString = LocalDateTime.now().format(iso_8601_formatter);

                //lấy file root đường dẫn tuyệt đối
                String rootFolder = Paths.get("").toAbsolutePath().toString();

                //tên file mới ko trùng
                String newFile = randomString + "_" + file.getOriginalFilename();

                //tạo đường dẫn tuyệt đối lưu file ảnh
                String filePath = rootFolder + File.separator + uploadDir + File.separator + newFile;

                //tạo phong bì File bò địa chỉ tuyệt đối vào ms lưu đc
                File destinationFile = new File(filePath);

                //mkdirs() đảm bảo tất cả các thư mục cha được tạo nếu chưa tồn tại
                destinationFile.getParentFile().mkdirs();

                //lưu file bằng try catch
                try {
                    file.transferTo(destinationFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //xóa file ảnh cũ nếu cần (không bắt buộc)
                Path delPath = Path.of(rootFolder + File.separator + uploadDir + File.separator + entityEdit.getImage());

                // xóa file nếu tồn tại
                try {
                    Files.deleteIfExists(delPath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //cập nhật tên file ảnh mới vào entity
                entityEdit.setImage(newFile);
            }

            if (objUpdate.getShortDescription() != null && !objUpdate.getShortDescription().isEmpty()) {
                entityEdit.setShortDescription(objUpdate.getShortDescription());
            }
            if (objUpdate.getDescription() != null && !objUpdate.getDescription().isEmpty()) {
                entityEdit.setDescription(objUpdate.getDescription());

            }
            if (objUpdate.getStandardCost() != null) {
                entityEdit.setStandardCost(objUpdate.getStandardCost());
            }
            if (objUpdate.getListPrice() != null) {
                entityEdit.setListPrice(objUpdate.getListPrice());
            }
            if (objUpdate.getQuantityPerUnit() != null && !objUpdate.getQuantityPerUnit().isEmpty()) {
                entityEdit.setQuantityPerUnit(objUpdate.getQuantityPerUnit());
            }
            if (objUpdate.getDiscontinued() != null) {
                entityEdit.setDiscontinued(objUpdate.getDiscontinued());
            }
            if (objUpdate.getIsFeatured() != null) {
                entityEdit.setIsFeatured(objUpdate.getIsFeatured());
            }
            if (objUpdate.getIsNew() != null) {
                entityEdit.setIsNew(objUpdate.getIsNew());
            }
            if (objUpdate.getCategoryId() != null) {
                entityEdit.setCategoryId(objUpdate.getCategoryId());
            }
            if (objUpdate.getSupplierId() != null) {
                entityEdit.setSupplierId(objUpdate.getSupplierId());
            }

            //lưu entity đã chỉnh sửa vào db
            ShopProduct savedEntity = shopProductRepository.save(entityEdit);

            //trả về response
            response.put("data", savedEntity);
            response.put("statuscode", 200);
            response.put("msg", "Cập nhật sản phẩm thành công oh yeah ");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg", "Không tìm thấy sản phẩm cần cập nhật ");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // delete product
    public ResponseEntity<Map<String, Object>> deleteProduct (Integer id) {
        //tạo response lưu kết quả trả về
        Map<String, Object> response = new HashMap<>();

        //kêu thủ kho repo tìm sp theo id
        Optional<ShopProduct> optFound = shopProductRepository.findById(id);

        //kiểm tra sp có tồn tại ko
        if (optFound.isPresent()) {
            //lấy quà ra khỏi hộp optinal
            ShopProduct entityDelete =  optFound.get();

            //xử lý xóa file ảnh khỏi server nếu cần
            String rootFolder = Paths.get("").toAbsolutePath().toString();
            Path delPath = Path.of(rootFolder + File.separator + uploadDir + File.separator + entityDelete.getImage());

            // xóa file nếu tồn tại
            try{
                Files.deleteIfExists(delPath);
            } catch (IOException ex){
                ex.printStackTrace();
            }
            //xóa sp
            shopProductRepository.deleteById(id);

            //trả về response
            response.put("data", null);
            response.put("statuscode", 200);
            response.put("msg", "Xóa sản phẩm thành công ");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg", "Không tìm thấy sản phẩm cần xóa ");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
