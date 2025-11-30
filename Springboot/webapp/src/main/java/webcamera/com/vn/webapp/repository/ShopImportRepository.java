package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.admin.ShopImportAD;

@Repository
public interface ShopImportRepository extends CrudRepository<ShopImportAD, Integer>,
        PagingAndSortingRepository<ShopImportAD, Integer> {
}
