package webcamera.com.vn.webapp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.admin.ShopProductImageDTO_AD.ShopProductImageCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopProductImageDTO_AD.ShopProductImageUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.service.admin.ShopProductImageServiceAD;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/shopproductimages")
public class ShopProductImageControllerAD {

    //goi dau bep service vao
    @Autowired
    private ShopProductImageServiceAD shopProductImageService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortby){
        return shopProductImageService.getAllShopProductImage(pageNumber,pageSize,sortby);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestParam("file")MultipartFile file,
                                                      @RequestParam("data")String jsonData) {
        ObjectMapper objMapper = new ObjectMapper();

        ShopProductImageCreateRequestDTO_AD objDTO = null;
        try {
            objDTO = objMapper.readValue(jsonData, ShopProductImageCreateRequestDTO_AD.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return shopProductImageService.createShopProductImage(objDTO,file);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        ObjectMapper objMapper = new ObjectMapper();

        ShopProductImageUpdateRequestDTO_AD objDTO = null;
        try{
            objDTO = objMapper.readValue(jsonData, ShopProductImageUpdateRequestDTO_AD.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        return shopProductImageService.updateShopProductImage(id,objDTO,file);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return shopProductImageService.deleteShopProductImage(id);
    }
}
