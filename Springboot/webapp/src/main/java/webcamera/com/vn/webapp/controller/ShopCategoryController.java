package webcamera.com.vn.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webcamera.com.vn.webapp.service.ShopCategoryService;

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
}
