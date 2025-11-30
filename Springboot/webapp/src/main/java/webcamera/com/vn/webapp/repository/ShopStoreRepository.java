package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.admin.ShopStoreAD;

@Repository
public interface ShopStoreRepository extends CrudRepository<ShopStoreAD, Integer>,
        PagingAndSortingRepository<ShopStoreAD, Integer> {
    ShopStoreAD findByStoreName(String shopName);
}
