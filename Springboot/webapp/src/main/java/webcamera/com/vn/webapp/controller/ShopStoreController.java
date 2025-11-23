package webcamera.com.vn.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Pattern;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.ShopStoreDTO.ShopStoreCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopStoreDTO.ShopStoreUpdateRequestDTO;
import webcamera.com.vn.webapp.service.ShopStoreService;

import java.util.Map;

@RestController
@RequestMapping("/api/shopstores")
public class ShopStoreController {
    @Autowired
    private ShopStoreService shopStoreService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortby){
        return shopStoreService.getAllShopStorePagination(pageNumber,pageSize,sortby);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestParam("file")MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        ObjectMapper objMapper = new ObjectMapper();

        ShopStoreCreateRequestDTO objDTO = null;
        try{
          objDTO = objMapper.readValue(jsonData, ShopStoreCreateRequestDTO.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        return shopStoreService.createShopStore(objDTO,file);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id,
                                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        ObjectMapper objMapper = new ObjectMapper();

        ShopStoreUpdateRequestDTO objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopStoreUpdateRequestDTO.class);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return shopStoreService.updateShopStore(id, objDTO, file);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Integer id){
        return shopStoreService.deleteShopStore(id);
    }
}
