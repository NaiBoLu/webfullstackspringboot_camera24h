package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.ShopSupplier;

@Repository
public interface ShopSupplierRepository extends CrudRepository<ShopSupplier, Integer>,
        PagingAndSortingRepository<ShopSupplier, Integer> {
    ShopSupplier findBySupplierName(String name);
}
