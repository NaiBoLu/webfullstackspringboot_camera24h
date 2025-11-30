package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.admin.ShopProductAD;

@Repository
public interface ShopProductRepository extends CrudRepository<ShopProductAD, Integer>,
        PagingAndSortingRepository<ShopProductAD, Integer> {
}
