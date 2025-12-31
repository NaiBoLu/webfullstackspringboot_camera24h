package webcamera.com.vn.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer>,
        PagingAndSortingRepository<Permission, Integer> {

    @Query(value = """
                SELECT DISTINCT 
                                u.username, p.name, p.display_name
                FROM user_has_roles uhr
                JOIN users u on uhr.user_id = u.id
                JOIN role_has_permissions rhp on rhp.role_id = uhr.role_id
                JOIN permisions p on rhp.permission_id = p.id
                WHERE u.username = :username;
        """, nativeQuery = true

    )                
    List<Object[]>  findPermissionsRawByUsername(@Param("username") String username);
}
