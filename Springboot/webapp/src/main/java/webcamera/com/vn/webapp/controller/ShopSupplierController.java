package webcamera.com.vn.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.ShopSupplierDTO.ShopSupplierCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopSupplierDTO.ShopSupplierUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopSupplier;
import webcamera.com.vn.webapp.service.ShopSupplierService;

import java.util.Map;

@RequestMapping("/api/shopsuppliers")
@RestController
public class ShopSupplierController {
    @Autowired
    private ShopSupplierService shopSupplierService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortBy){
        return shopSupplierService.getAllShopSupplier(pageNumber, pageSize, sortBy);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        ObjectMapper objMapper = new ObjectMapper();

        ShopSupplierCreateRequestDTO objDTO = null;
        // truyền jsondata vào dto
        try {
            objDTO = objMapper.readValue(jsonData, ShopSupplierCreateRequestDTO.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        return shopSupplierService.createShopSuplier(objDTO, file);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        ObjectMapper objectMapper = new ObjectMapper();

        ShopSupplierUpdateRequestDTO objDTO = null;

        try{
            objDTO = objectMapper.readValue(jsonData, ShopSupplierUpdateRequestDTO.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        return shopSupplierService.updateShopSupplier(id, objDTO, file);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return shopSupplierService.deleteShopSupplier(id);
    }
}
