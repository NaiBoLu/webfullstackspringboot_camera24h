package webcamera.com.vn.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webcamera.com.vn.webapp.DTO.ShopCategoryDTO.ShopCategoryCreateRequestDTO;
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody ShopCategoryCreateRequestDTO res){
        //khoi tao bien luu response de trả về thông báo lỗi
        try{
            //goi den service luu csdl tu dto(gui len tu client thong qua create form dang ky user)
            return shopCategoryService.createShopCategory(res);
        }catch(Exception e){
            //khoi tao bien luu response de trả về thông báo lỗi
            Map<String, Object> response = new HashMap<>();

            response.put("data", e.getMessage());
            response.put("statuscode",501);
            response.put("msg","du lieu co loi vui long kiem tra lai");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /* delete - Delete
     * @PathVariable: anotation dc su dung de trich xuat gia thong qua url  api va anh xa no toi
     * tham so cua method controler nay,
     *  -> day la cach ma g ia tri cua id trong dg dan path dc truyen den tham so id cua mehotd delete
     * */

}
