package webcamera.com.vn.webapp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.admin.ShopStoreDTO_AD.ShopStoreCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopStoreDTO_AD.ShopStoreUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.service.admin.ShopStoreServiceAD;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/shopstores")
public class ShopStoreControllerAD {
    @Autowired
    private ShopStoreServiceAD shopStoreService;

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

        ShopStoreCreateRequestDTO_AD objDTO = null;
        try{
          objDTO = objMapper.readValue(jsonData, ShopStoreCreateRequestDTO_AD.class);
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

        ShopStoreUpdateRequestDTO_AD objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopStoreUpdateRequestDTO_AD.class);
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
