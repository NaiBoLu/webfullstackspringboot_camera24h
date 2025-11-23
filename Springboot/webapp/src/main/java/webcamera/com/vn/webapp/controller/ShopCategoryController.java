package webcamera.com.vn.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.ShopCategoryDTO.ShopCategoryCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopCategoryDTO.ShopCategoryUpdateRequestDTO;
import webcamera.com.vn.webapp.service.ShopCategoryService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shopcategories")
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(defaultValue = "3") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortby){

        return shopCategoryService.getAllShopCategory(pageNumber,pageSize,sortby);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestParam("file")MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        //khoi tao bien luu response de trả về thông báo lỗi
        ObjectMapper objMapper = new ObjectMapper();
        // tao dto rong
        ShopCategoryCreateRequestDTO optDTO = null;

        try{
            //goi den service luu csdl tu dto(gui len tu client thong qua create form dang ky user
            optDTO = objMapper.readValue(jsonData, ShopCategoryCreateRequestDTO.class);

        }catch(Exception e){
            e.printStackTrace();
        }

        return shopCategoryService.createShopCategory(optDTO, file);

    }

    // update
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id,
                                                      @RequestParam(value = "file", required = false ) MultipartFile file,
                                                      @RequestParam("data") String jsonData){
        // tao object mapper
        ObjectMapper objMapper = new ObjectMapper();

        //tao dto update rong de truyen jsondata
        ShopCategoryUpdateRequestDTO objDTO = null;

        try{
            objDTO = objMapper.readValue(jsonData , ShopCategoryUpdateRequestDTO.class);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        return shopCategoryService.updateShopCateogyr(id, objDTO, file);
    }


    /* delete - Delete
     * @PathVariable: anotation dc su dung de trich xuat gia thong qua url  api va anh xa no toi
     * tham so cua method controler nay,
     *  -> day la cach ma g ia tri cua id trong dg dan path dc truyen den tham so id cua mehotd delete
     * */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        return shopCategoryService.delteShopcategory(id);
    }

}
