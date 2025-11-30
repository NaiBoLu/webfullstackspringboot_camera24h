package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.admin.ShopSupplierAD;

@Repository
public interface ShopSupplierRepository extends CrudRepository<ShopSupplierAD, Integer>,
        PagingAndSortingRepository<ShopSupplierAD, Integer> {
    ShopSupplierAD findBySupplierName(String name);
}
