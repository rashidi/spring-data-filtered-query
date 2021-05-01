package zin.rashidi.springdatafilterquery.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static zin.rashidi.springdatafilterquery.user.UserStatus.ACTIVE;
import static zin.rashidi.springdatafilterquery.user.UserStatus.INACTIVE;
import static zin.rashidi.springdatafilterquery.user.UserTestHelper.activeUser;
import static zin.rashidi.springdatafilterquery.user.UserTestHelper.inactiveUser;

/**
 * @author Rashidi Zin
 */
@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Test
    void findByStatusIsNot() {
        repository.saveAll(List.of(activeUser(), inactiveUser()));

        assertThat(repository.findByStatusIsNot(INACTIVE))
                .hasSize(1)
                .extracting("username", "status")
                .containsOnly(tuple(activeUser().getUsername(), ACTIVE));
    }

}
