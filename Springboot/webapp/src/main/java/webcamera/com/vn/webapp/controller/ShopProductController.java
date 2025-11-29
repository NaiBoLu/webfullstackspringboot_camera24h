package webcamera.com.vn.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.ProductDTO.ShopProductCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ProductDTO.ShopProductUpdateRequestDTO;
import webcamera.com.vn.webapp.service.ShopProductService;

import java.util.Map;

@RestController
@RequestMapping("/api/shop-products")
public class ShopProductController {
    //gọi đầu bếp service nấu ăn
    @Autowired
    private ShopProductService shopProductService;

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
        ShopProductCreateRequestDTO objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopProductCreateRequestDTO.class);
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
        ShopProductUpdateRequestDTO objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopProductUpdateRequestDTO.class);
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
