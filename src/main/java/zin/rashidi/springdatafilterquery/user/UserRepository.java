package zin.rashidi.springdatafilterquery.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Rashidi Zin
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByStatusIsNot(UserStatus excludeStatus);

}
