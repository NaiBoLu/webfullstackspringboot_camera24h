package webcamera.com.vn.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webcamera.com.vn.webapp.DTO.ShopImportDTO.ShopImportCreateRequestDTO;
import webcamera.com.vn.webapp.DTO.ShopImportDTO.ShopImportUpdateRequestDTO;
import webcamera.com.vn.webapp.entity.ShopImport;
import webcamera.com.vn.webapp.repository.ShopImportRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopImportService {

    @Autowired
    private ShopImportRepository shopImportRepo;

    //getall co phan trang
    public ResponseEntity<Map<String, Object>> getAllShopImport(Integer pageNumber, Integer pageSize, String sortby){
        //tao response luu kq tra ve
        Map<String, Object> response = new HashMap<>();

        //xu ly phan trang
        Pageable pageAble = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortby)); // yeu cau sap xep
        Page<ShopImport> pageResult = shopImportRepo.findAll(pageAble); // ket qua tra ve

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


    //create shop import
    public ResponseEntity<Map<String, Object>> createShopImport(ShopImportCreateRequestDTO objCreate){
        Map<String, Object> response = new HashMap<>();

        ShopImport newEntity = new ShopImport();

        newEntity.setStoreId(objCreate.getStoreId());
        newEntity.setEmployeeId(objCreate.getEmployeeId());
        newEntity.setImportDate(LocalDateTime.now());

        //dung repo luu lai ket qua
        ShopImport createEntity = shopImportRepo.save(newEntity);

        response.put("data",createEntity);
        response.put("statuscode",200);
        response.put("msg","tao thanh cong oh yeah");

        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    //update shop import
    public ResponseEntity<Map<String, Object>> updateShopImport(Integer id, ShopImportUpdateRequestDTO objUpdate){
        Map<String, Object> response = new HashMap<>();

        Optional<ShopImport> optFound = shopImportRepo.findById(id);

        if(optFound.isPresent()){
            ShopImport entityUpdate = optFound.get();

            if(objUpdate.getStoreId() != null){
                entityUpdate.setStoreId(objUpdate.getStoreId());
            }
            if(objUpdate.getEmployeeId() != null){
                entityUpdate.setEmployeeId(objUpdate.getEmployeeId());
            }
            entityUpdate.setImportDate(LocalDateTime.now());

            //nho repo luu lai entity vua update
            shopImportRepo.save(entityUpdate);

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

    public ResponseEntity<Map<String, Object>> deleteShopImport(Integer id){
        Map<String, Object> response = new HashMap<>();

        Optional<ShopImport> optFound = shopImportRepo.findById(id);

        if(optFound.isPresent()){

            ShopImport delEntity = optFound.get();

            shopImportRepo.delete(delEntity);

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
