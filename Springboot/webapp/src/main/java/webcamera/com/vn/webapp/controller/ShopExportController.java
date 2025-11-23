package webcamera.com.vn.webapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webcamera.com.vn.webapp.DTO.ShopExportDTO.ShopExportCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopExportDTO.ShopExportUpdateRequestDTO;
import webcamera.com.vn.webapp.service.ShopExportService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shopexports")
public class ShopExportController {
    @Autowired
    private ShopExportService shopExportService;

    //get all
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortby){
        return shopExportService.getAllShopExport(pageNumber,pageSize,sortby);
    }

    //create
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ShopExportCreateRequestDTO objDTO){
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
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, ShopExportUpdateRequestDTO objDTO){
        return shopExportService.updateShopExport(id,objDTO);
    }

    //delete
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return shopExportService.deleteShopExport(id);
    }

}
