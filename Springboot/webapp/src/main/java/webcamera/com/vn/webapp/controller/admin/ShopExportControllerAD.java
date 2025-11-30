package webcamera.com.vn.webapp.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webcamera.com.vn.webapp.DTO.admin.ShopExportDTO_AD.ShopExportCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopExportDTO_AD.ShopExportUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.service.admin.ShopExportServiceAD;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/shopexports")
public class ShopExportControllerAD {
    @Autowired
    private ShopExportServiceAD shopExportService;

    //get all
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortby){
        return shopExportService.getAllShopExport(pageNumber,pageSize,sortby);
    }

    //create
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ShopExportCreateRequestDTO_AD objDTO){
        Map<String, Object> response = new HashMap<>();

        try{
            return shopExportService.createShopExport(objDTO);
        }catch(Exception ex){
            response.put("data","Co loi vui long kiem tra lai");
            response.put("statuscode",500);
            response.put("msg",ex.getMessage());

            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, ShopExportUpdateRequestDTO_AD objDTO){
        return shopExportService.updateShopExport(id,objDTO);
    }

    //delete
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return shopExportService.deleteShopExport(id);
    }

}
