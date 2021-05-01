package zin.rashidi.springdatafilterquery.user;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static zin.rashidi.springdatafilterquery.user.UserStatus.ACTIVE;

/**
 * @author Rashidi Zin
 */
public class UserFilteredQueryRepository extends SimpleJpaRepository<User, Long> {

    public UserFilteredQueryRepository(Class<User> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<User> findAll() {
        var probe = new User();

        probe.setStatus(ACTIVE);

        return super.findAll(Example.of(probe));
    }

}
