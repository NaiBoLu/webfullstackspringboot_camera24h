package webcamera.com.vn.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.ShopCategoryDTO.ShopCategoryCreateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopCategory;
import webcamera.com.vn.webapp.repository.ShopCategoryRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShopCategoryService {
    //@Autowired gọi thủ kho repo vô để sử dụng
    @Autowired
    private ShopCategoryRepository shopCategoryRepo;

    /*I - GET ->lay va do du lieu co phan trang*/
    public ResponseEntity<Map<String, Object>> getAllShopCategory(int pageNumber, int pageSize, String sortby){
        //tạo response trả kết quả về kiểu thông báo lmao
        Map<String, Object> response = new HashMap<>();


        //b- yeu cau repository lay du lieu -> goi den repository goi den thao tac crud
        /*
         * Pageable: la mot giao dien trong spring data dc su dung de ho tro phan trang
         * sap xep trang
         *  + pageNUmber: trang so may(trang dang xem)
         *  + pageSize:  tong so luong trang
         *  + sortBy: sap xep trang cot nao: id or theo ten name...
         * */
        Pageable  pageable = PageRequest.of(pageNumber - 1,pageSize, Sort.by(sortby)); // yeu cau
        Page<ShopCategory> pageResult = shopCategoryRepo.findAll(pageable); // keets qua

        if(pageResult.hasContent()){
            //tra ket qua cho nguoi dung -> tra theo chuan restfull APi sieu cap vip pro
            response.put("data",pageResult.getContent());
            response.put("statuscode",200);
            response.put("msg","get du lieu thanh cong oh yeah da qua xa da");

            response.put("current",pageNumber);
            response.put("isFirst",pageResult.isFirst()); // phair trang dau tien ko
            response.put("isLast",pageResult.isLast()); // trang cuoi ko
            response.put("Previous",pageResult.hasPrevious()); // trang truoc
            response.put("Next",pageResult.hasNext()); // trang ke tiep
            response.put("TotalPage",pageResult.getTotalPages());
            response.put("TotalElement",pageResult.getTotalElements());


            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("data",null);
            response.put("statuscode", 404);
            response.put("msg","Khong tim thay du lieu huhuhu");

            return  new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

        }
    }

    /*II - Post(create)*/
//    public ResponseEntity<Map<String, Object>> createShopCategory(ShopCategoryCreateRequestDTO objCreate) {
//        //a - khoi tao bien response de luu tru ket qua tra ve
//        //a - khoi tao bien response de luu tru ket qua tra ve
//        Map<String, Object> response = new HashMap<>();
//
//        //c-1 khoi tao shopcategoryEntity để truyền createDTO vào
//        ShopCategory newEntity = new ShopCategory();"d"
//        newEntity.setCategoryCode(objCreate.getCategoryCode());
//        newEntity.setCategoryName(objCreate.getCategoryName());
//        newEntity.setDescription(objCreate.getDescription());
//        newEntity.setImage(objCreate.getImage());
//
//        //luu lại bang repo
//        ShopCategory createEntity = shopCategoryRepo.save(newEntity); // mua dau
//
//        response.put()
//
//    }

}
