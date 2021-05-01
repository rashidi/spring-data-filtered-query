package zin.rashidi.springdatafilterquery.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static zin.rashidi.springdatafilterquery.user.UserStatus.ACTIVE;
import static zin.rashidi.springdatafilterquery.user.UserTestHelper.activeUser;
import static zin.rashidi.springdatafilterquery.user.UserTestHelper.inactiveUser;

/**
 * @author Rashidi Zin
 */
@DataJpaTest(includeFilters = @Filter(classes = UserRepositoryConfiguration.class, type = ASSIGNABLE_TYPE))
class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Test
    void findAll() {
        repository.saveAll(List.of(activeUser(), inactiveUser()));

        assertThat(repository.findAll())
                .hasSize(1)
                .extracting("username", "status")
                .containsOnly(tuple(activeUser().getUsername(), ACTIVE));
    }

}
