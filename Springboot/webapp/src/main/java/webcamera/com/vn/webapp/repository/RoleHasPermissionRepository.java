package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.RoleHasPermissions;

@Repository
public interface RoleHasPermissionRepository extends CrudRepository<RoleHasPermissions, Integer>,
        PagingAndSortingRepository<RoleHasPermissions, Integer> {
}
