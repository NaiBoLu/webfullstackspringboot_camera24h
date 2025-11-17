package webcamera.com.vn.webapp.service;

import jakarta.validation.ConstraintViolationException;
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
import webcamera.com.vn.webapp.DTO.ShopCategoryDTO.ShopCategoryUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopCategory;
import webcamera.com.vn.webapp.repository.ShopCategoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<Map<String, Object>> createShopCategory(ShopCategoryCreateRequestDTO objCreate) {
        //a - khoi tao bien response de luu tru ket qua tra ve
        //a - khoi tao bien response de luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //c-1 khoi tao shopcategoryEntity để truyền createDTO vào
        ShopCategory newEntity = new ShopCategory();

        newEntity.setCategoryCode(objCreate.getCategoryCode());
        newEntity.setCategoryName(objCreate.getCategoryName());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setImage(objCreate.getImage());

        // c-2 yeu cau repository luu lai khoi tao tren
        // thuc hien nhan ten dk username va tien hanh kiem tra tranh trung ten username khi dang ky
        ShopCategory existingCategory = shopCategoryRepo.findByCategoryName(objCreate.getCategoryName());

        if(existingCategory != null){
            //nem loi thong bao de khong cho phep tao trung ten
            throw new ConstraintViolationException("ten ban dung da ton tai vui long dat ten khac",null);
        }else{
            ShopCategory createEntity = shopCategoryRepo.save(newEntity);

            //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
            response.put("data", createEntity);
            response.put("statuscode", 200);
            response.put("msg", " create thanh cong");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

//    /*III - Put(Update0*/
//    public ResponseEntity<Map<String, Object>> updateShopCateogyr(Integer id, ShopCategoryUpdateRequestDTO objEdit){
//        //khoi tao
//    }

    /*IV- Delete(xoa)*/
    public ResponseEntity<Map<String, Object>> delteShopcategory(Integer id){
        //a - khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repository goi method tim kiem id can xoa
        /*
         * Optional:
         *  + la mot lop trong java(java.util.Optional) dc gioi thieu tu java 8
         *  + no la mot container object hộp chứa quà co the chua mot gia tri khong null  hoac rong emtpy
         *  + muc tieu chinh Optional la giup iam thieu loi NullPointerException khi ma minhf
         * xu ly voi cac gia tri null
         * */
        Optional<ShopCategory> optFound = shopCategoryRepo.findById(id);
        if(optFound.isPresent()){
            //neu ton tai id can tim thi lay no ra khỏi hộp -> ghi nhan no vao entity
            ShopCategory delEntity = optFound.get();

            //goi thu kho repo xoa no
            shopCategoryRepo.delete(delEntity);

            //tra ve ket qua nguoi dung
            response.put("data",null);
            response.put("statuscode",201);
            response.put("msg","delete thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            //tra ve chuan restfull api thong bao la khong ton tai id can xoa
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","khong ton tai huhuhu");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

}
