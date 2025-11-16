package webcamera.com.vn.webapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webcamera.com.vn.webapp.DTO.ShopStoreDTO.ShopStoreCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopStoreDTO.ShopStoreUpdateRequestDTO;
import webcamera.com.vn.webapp.service.ShopStoreService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shopstores")
public class ShopStoreController {

    //goi khoi tao lop services trong controller cua user đầu bếp nấu ăn
    @Autowired
    private ShopStoreService shopStoreService;

    /*I - render GET lấy tất cả dữ liệu*/
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getIndex(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                        @RequestParam(defaultValue = "3") Integer pageSize,
                                                        @RequestParam(defaultValue = "id") String sortby){
        //nho sevice goi thuc thi get all du lieu
        return shopStoreService.getAllShopStorePagination(pageNumber, pageSize, sortby);
    }

    /*II - POST create */
    /*
     + @PostMapping:thiet lap mapping theo chuan method post - create trong crud cua repository cuar spring boot
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> Create(@RequestBody @Valid ShopStoreCreateRequestDTO res){
        //xu ly bat loi nem ra throw tu serive bang cach dung try catch
        try{
            //goi den service luu csdl tu dto(dc gui len tu client thong qua create form dang ky user)
            return shopStoreService.createShopStore(res);

        }catch(Exception ex){
            //khoi tao bien luu response de trả về thông báo lỗi
            Map<String, Object> response = new HashMap<>();
            response.put("data", ex.getMessage());
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Integer id){
        return shopStoreService.deleteShopStore(id);
    }

    /*update - PUT*/
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody ShopStoreUpdateRequestDTO res){
        return shopStoreService.updateShopStore(id, res);
    }


}
