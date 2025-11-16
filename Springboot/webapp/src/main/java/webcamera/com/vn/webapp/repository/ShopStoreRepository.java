package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.ShopStore;

@Repository
public interface ShopStoreRepository extends CrudRepository<ShopStore, Integer>,
        PagingAndSortingRepository<ShopStore, Integer> {
    ShopStore findByStoreName(String shopName);
}
