package webcamera.com.vn.webapp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.admin.ProductDTO_AD.ShopProductCreateRequestDTOAD;
import webcamera.com.vn.webapp.DTO.admin.ProductDTO_AD.ShopProductUpdateRequestDTOAD;
import webcamera.com.vn.webapp.service.admin.ShopProductServiceAD;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/shop-products")
public class ShopProductControllerAD {
    //gọi đầu bếp service nấu ăn
    @Autowired
    private ShopProductServiceAD shopProductService;

    //get all có phân trang
    @GetMapping("/")
    private ResponseEntity<Map<String, Object>> getAllProduct(Integer pageNumber, Integer pageSize, String sortby){
            return shopProductService.getAllProduct(pageNumber, pageSize, sortby);
    }

    //create new product
    @PostMapping("/create")
    private ResponseEntity<Map<String, Object>> create(@Valid @RequestParam("file")MultipartFile file,
                                                       @RequestParam("data") String jsonData){
        ObjectMapper objMapper = new ObjectMapper();
        ShopProductCreateRequestDTOAD objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopProductCreateRequestDTOAD.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }
        return shopProductService.createProduct(objDTO, file);
    }

    //update product
    @PutMapping("/update/{id}")
    private ResponseEntity<Map<String, Object>> update(@PathVariable Integer id,
                                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                                       @RequestParam("data") String jsonData){
        ObjectMapper objMapper = new ObjectMapper();
        ShopProductUpdateRequestDTOAD objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopProductUpdateRequestDTOAD.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }
        return shopProductService.updateProduct(id, objDTO, file);
    }

    //delete product
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        return shopProductService.deleteProduct(id);
    }
}
