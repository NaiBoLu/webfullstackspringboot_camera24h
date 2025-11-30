package webcamera.com.vn.webapp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webcamera.com.vn.webapp.DTO.admin.ShopCategoryDTO_AD.ShopCategoryCreateRequestDTO_AD;
import webcamera.com.vn.webapp.DTO.admin.ShopCategoryDTO_AD.ShopCategoryUpdateRequestDTO_AD;
import webcamera.com.vn.webapp.service.admin.ShopCategoryServiceAD;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/shopcategories")
public class ShopCategoryControllerAD {

    @Autowired
    private ShopCategoryServiceAD shopCategoryService;

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
        ShopCategoryCreateRequestDTO_AD optDTO = null;

        try{
            //goi den service luu csdl tu dto(gui len tu client thong qua create form dang ky user
            optDTO = objMapper.readValue(jsonData, ShopCategoryCreateRequestDTO_AD.class);

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
        ShopCategoryUpdateRequestDTO_AD objDTO = null;

        try{
            objDTO = objMapper.readValue(jsonData , ShopCategoryUpdateRequestDTO_AD.class);
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
