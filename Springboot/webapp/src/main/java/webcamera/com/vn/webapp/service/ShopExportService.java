package webcamera.com.vn.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.ShopExportDTO.ShopExportCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopExportDTO.ShopExportUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopExport;
import webcamera.com.vn.webapp.repository.ShopExportRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopExportService {
    @Autowired
    private ShopExportRepository shopExportRepo;

    //get all co phan trang shop export
    public ResponseEntity<Map<String, Object>> getAllShopExport(Integer pageNumber, Integer pageSize, String sortby){
        //tao response luu kq tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageAble = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortby)); // yeu cau sap xep
        Page<ShopExport> pageResult = shopExportRepo.findAll(pageAble); // ket qua tra ve

        if(pageResult.hasContent()){
            //dung response chuan restful API
            response.put("data",pageResult.getContent());
            response.put("statuscode",200);
            response.put("msg","lay ket qua thanh cong oh yeah!");

            response.put("pageCurrent",pageNumber);
            response.put("isFirst",pageResult.isFirst());
            response.put("isLast",pageResult.isLast());
            response.put("hasPrevious",pageResult.hasPrevious());
            response.put("hasNext",pageResult.hasNext());
            response.put("Totalelement",pageResult.getTotalElements());
            response.put("Total Page",pageResult.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            //dung response chuan restful API
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","lay ket qua ko thanh cong oh no!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //create shop export
    public ResponseEntity<Map<String, Object>> createShopExport(ShopExportCreateRequestDTO objCreate){
        Map<String, Object> response = new HashMap<>();

        ShopExport newEntity = new ShopExport();

        newEntity.setStoreId(objCreate.getStoreId());
        newEntity.setEmployeeId(objCreate.getEmployeeId());
        newEntity.setExportDate(LocalDateTime.now());
        newEntity.setDescription(objCreate.getDescription());
        newEntity.setOrderId(objCreate.getOrderId());

        //dung repo luu lai ket qua
        ShopExport createEntity = shopExportRepo.save(newEntity);

        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","tao thanh cong oh yeah");

        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    //update shop export

    public ResponseEntity<Map<String, Object>> updateShopExport(Integer id, ShopExportUpdateRequestDTO objUpdate){
        Map<String, Object> response = new HashMap<>();

        Optional<ShopExport> optFound = shopExportRepo.findById(id);

        if(optFound.isPresent()){
            ShopExport entityUpdate = optFound.get();

            if(objUpdate.getStoreId() != null){
                entityUpdate.setStoreId(objUpdate.getStoreId());
            }
            if(objUpdate.getEmployeeId() != null){
                entityUpdate.setEmployeeId(objUpdate.getEmployeeId());
            }

            entityUpdate.setExportDate(LocalDateTime.now());

            if(objUpdate.getDescription() != null && !objUpdate.getDescription().isEmpty()){
                entityUpdate.setDescription(objUpdate.getDescription());
            }
            if(objUpdate.getOrderId() != null){
                entityUpdate.setOrderId(objUpdate.getOrderId());
            }

            //nho repo luu lai entity vua update
            shopExportRepo.save(entityUpdate);

            response.put("data",entityUpdate);
            response.put("statuscode",200);
            response.put("msg","update thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","update ko thanh cong oh no");

            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete shop export
    public ResponseEntity<Map<String, Object>> deleteShopExport(Integer id){
        Map<String, Object> response = new HashMap<>();

        Optional<ShopExport> optFound = shopExportRepo.findById(id);

        if(optFound.isPresent()){

            ShopExport delEntity = optFound.get();

            shopExportRepo.delete(delEntity);

            response.put("data",delEntity);
            response.put("statuscode",200);
            response.put("msg","delete thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.put("data",null);
            response.put("statuscode",404);
            response.put("msg","delete ko thanh cong oh yeah");

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

}
