package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.ShopProductImage;

@Repository
public interface ShopProductImageRepository extends CrudRepository<ShopProductImage, Integer>,
        PagingAndSortingRepository<ShopProductImage, Integer> {
}
