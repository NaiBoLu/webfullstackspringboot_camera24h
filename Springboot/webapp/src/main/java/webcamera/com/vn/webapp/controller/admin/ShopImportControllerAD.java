package webcamera.com.vn.webapp.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webcamera.com.vn.webapp.DTO.admin.ShopImportDTO_AD.ShopImportCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopImportDTO_AD.ShopImportUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.service.admin.ShopImportServiceAD;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/admin/shopimports/")
@RestController
public class ShopImportControllerAD {

    @Autowired
    private ShopImportServiceAD shopImportService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortby){
     return shopImportService.getAllShopImport(pageNumber,pageSize,sortby);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ShopImportCreateRequestDTO_AD objDTO){
        Map<String, Object> response = new HashMap<>();

        try{
            return shopImportService.createShopImport(objDTO);
        }catch(Exception ex){
            response.put("data","Co loi vui long kiem tra lai");
            response.put("statuscode",500);
            response.put("msg",ex.getMessage());

            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, ShopImportUpdateRequestDTO_AD objDTO){
            return shopImportService.updateShopImport(id,objDTO);
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return shopImportService.deleteShopImport(id);
    }


}
