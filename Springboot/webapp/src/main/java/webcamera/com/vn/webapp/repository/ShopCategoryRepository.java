package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.ShopCategory;

@Repository
public interface ShopCategoryRepository extends CrudRepository<ShopCategory, Integer>,
        PagingAndSortingRepository<ShopCategory, Integer> {
    // tìm kiếm theo tên
    ShopCategory findByCategoryName(String categoryName);
}
