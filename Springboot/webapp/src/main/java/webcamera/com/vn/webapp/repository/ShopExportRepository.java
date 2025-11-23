package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.ShopExport;

@Repository
public interface ShopExportRepository extends CrudRepository<ShopExport, Integer>,
        PagingAndSortingRepository<ShopExport, Integer> {
}
