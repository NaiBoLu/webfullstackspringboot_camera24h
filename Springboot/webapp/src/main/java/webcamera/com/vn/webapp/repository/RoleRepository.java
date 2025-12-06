package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>,
        PagingAndSortingRepository<Role, Integer> {
}
