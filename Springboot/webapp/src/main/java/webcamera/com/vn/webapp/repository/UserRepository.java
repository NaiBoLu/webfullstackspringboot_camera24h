package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.admin.UserAD;

@Repository
public interface UserRepository  extends CrudRepository<UserAD, Integer>,
        PagingAndSortingRepository<UserAD, Integer> {
    UserAD findByUsername(String username);
}
