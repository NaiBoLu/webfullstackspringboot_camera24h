package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.admin.ShopCategoryAD;

@Repository
public interface ShopCategoryRepository extends CrudRepository<ShopCategoryAD, Integer>,
        PagingAndSortingRepository<ShopCategoryAD, Integer> {
    // tìm kiếm theo tên
    ShopCategoryAD findByCategoryName(String categoryName);
}
