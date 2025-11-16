package webcamera.com.vn.webapp.service;

import jakarta.validation.ConstraintViolationException;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.ShopStoreDTO.ShopStoreCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopStoreDTO.ShopStoreUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopStore;
import webcamera.com.vn.webapp.entity.User;
import webcamera.com.vn.webapp.repository.ShopStoreRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopStoreService {

    // thêm thủ kho repo vào để phân trang và crud dữ liệu
    @Autowired
    private ShopStoreRepository shopStoreRepo;

    /*I - GET ->lay va do du lieu co phan trang*/
    public ResponseEntity<Map<String, Object>> getAllShopStorePagination(int pageNumber, int pageSize, String sortby){
        //a - khoi tao bien respone luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //b- yeu cau repository lay du lieu -> goi den repository goi den thao tac crud
        /*
         * Pageable: la mot giao dien trong spring data dc su dung de ho tro phan trang
         * sap xep trang
         *  + pageNUmber: trang so may(trang dang xem)
         *  + pageSize:  tong so luong trang
         *  + sortBy: sap xep trang cot nao: id or theo ten name...
         * */
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortby)); // đây là miêu tả yêu cầu
        Page<ShopStore> pageResult = shopStoreRepo.findAll(pageable); // trả về kết quả page phân trang theo yêu cầu ở trên
        if(pageResult.hasContent()){ // nếu pageresult có nội dung
            //tra ket qua cho nguoi dung -> tra theo chuan restfull APi sieu cap vip pro
            response.put("data",pageResult.getContent());
            response.put("statuscode",201);
            response.put("msg","lay du lieu thanh cong oh yeah babe!!");

            response.put("currentpage",pageNumber); // trang hiện tại
            response.put("isFirst",pageResult.isFirst()); // trang đầu tiên
            response.put("isLast",pageResult.isLast()); // hiện trang cuối cùng
            response.put("hasNext",pageResult.hasNext()); // trang tiếp theo
            response.put("hasPrevious",pageResult.hasPrevious()); // trang trước đó
            response.put("totalPage",pageResult.getTotalPages()); // tất cả các trang
            response.put("totalElement",pageResult.getTotalElements()); // lol

            return new ResponseEntity<>(response,HttpStatus.OK); // trả về entity và thông báo ở trên cho client

        }else {
            // trả về lỗi ko tìm thấy trang nào
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg","Khong co du lieu lmao");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    /*II - Post(create) tạo thêm 1 shopstores mới*/
    public ResponseEntity<Map<String, Object>> createShopStore(ShopStoreCreateRequestDTO objCreate) {
        //a - khoi tao bien response de luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        //c-1 khoi tao ShopStoreEntity truyền requestDTO vào
        ShopStore newEntity = new ShopStore();

        newEntity.setStoreCode(objCreate.getStoreCode());
        newEntity.setStoreName(objCreate.getStoreName());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setImage(objCreate.getImage());

        // c-2 yeu cau repository luu lai khoi tao tren
        // thuc hien nhan ten dk username va tien hanh kiem tra tranh trung ten username khi dang ky
        ShopStore existingShop = shopStoreRepo.findByStoreName(objCreate.getStoreName());

        // c-3 thuc hien kiem tra ds data trong mysql co trung ten username nao khong
        if(existingShop != null){
            //nem loi thong bao de khong cho phep tao trung ten
            throw new ConstraintViolationException("Ten ban dung da ton tai vui long chon ten khac ", null);
        }else{
            ShopStore createEntity = shopStoreRepo.save(newEntity);

            //c-4 tra ve ket qua cho nguoi dung theo chuan restfullAPI
            response.put("data", createEntity);
            response.put("statuscode", 200);
            response.put("msg", " create thanh cong");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /*III - Put(Update0*/
    public ResponseEntity<Map<String, Object>> updateShopStore(Integer id, ShopStoreUpdateRequestDTO objEdit){
        // khoi tao bien response luu tru ket qua tra ve
        Map<String, Object> response = new HashMap<>();

        // nho repo tim kiem entity(dua tren id) ma muon update
        Optional<ShopStore> optFound = shopStoreRepo.findById(id);

        //kiem tra va cap nha cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan
        if(optFound.isPresent()){
            //nhan dc id vua tim kiem va gan vao entity cua uder de doi chung lấy ra khỏi hộp quà
            ShopStore entityEdit = optFound.get();

            //kiem tra va cap nhat cac truong tt null hoawc empty -> tien hanh bo qua va ghi nhan nếu trống thì bỏ qua
            if(objEdit.getStoreCode() != null && !objEdit.getStoreCode().isEmpty()){
                entityEdit.setStoreCode(objEdit.getStoreCode());
            }
            if(objEdit.getStoreName() != null && !objEdit.getStoreName().isEmpty()){
                entityEdit.setStoreName(objEdit.getStoreName());
            }
            if(objEdit.getDescription() != null && !objEdit.getDescription().isEmpty()){
                entityEdit.setDescription(objEdit.getDescription());
            }
            if(objEdit.getImage() != null && !objEdit.getImage().isEmpty()){
                entityEdit.setImage(objEdit.getImage());
            }

            //nho repo luu lại
            shopStoreRepo.save(entityEdit);


            //tra ve thogn bao chuan restfull api
            response.put("data", entityEdit);
            response.put("statuscode", 200);
            response.put("msg", "update thanh cong roi yeah yeah");

            return new ResponseEntity<>(response , HttpStatus.OK);

        }else{
            response.put("data", null);
            response.put("statuscode", 404);
            response.put("msg", " update khong thanh cong");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*IV- Delete(xoa)*/
    public ResponseEntity<Map<String, Object>> deleteShopStore(Integer id){
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
        Optional<ShopStore> optFound = shopStoreRepo.findById(id);
        if(optFound.isPresent()){
            //neu ton tai id can tim thi lay no ra khỏi hộp -> ghi nhan no vao entity
            ShopStore delEntity = optFound.get();

            //gọi repo thủ kho xóa
            shopStoreRepo.delete(delEntity);

            //tra ve ket qua nguioi dung chuan restfull api
            response.put("data",null);
            response.put("statuscode",200);
            response.put("msg","delete thanh cong lol");

            return new ResponseEntity<>(response, HttpStatus.OK);

        }else{
            //tra ve chuan restfull api thong bao la khong ton tai id can xoa
            response.put("data", null);
            response.put("statuscode",404);
            response.put("msg","khong ton tại huhuhu");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



}
