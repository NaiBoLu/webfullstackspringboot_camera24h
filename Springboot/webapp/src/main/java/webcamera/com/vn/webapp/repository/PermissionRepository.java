package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer>,
        PagingAndSortingRepository<Permission, Integer> {
}
