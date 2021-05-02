package zin.rashidi.springdatafilterquery.user;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static zin.rashidi.springdatafilterquery.user.UserStatus.ACTIVE;

/**
 * @author Rashidi Zin
 */
public class UserFilteredQueryRepository extends SimpleJpaRepository<User, Long> {

    public UserFilteredQueryRepository(JpaEntityInformation<User, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public List<User> findAll() {
        var probe = new User();

        probe.setStatus(ACTIVE);

        return super.findAll(Example.of(probe));
    }

}
