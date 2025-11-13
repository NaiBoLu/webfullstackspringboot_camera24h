package webcamera.com.vn.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.User;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer> {
    User findByUsername(String username);
}
