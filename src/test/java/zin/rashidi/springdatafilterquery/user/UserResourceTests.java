package zin.rashidi.springdatafilterquery.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static zin.rashidi.springdatafilterquery.user.UserStatus.ACTIVE;
import static zin.rashidi.springdatafilterquery.user.UserTestHelper.activeUser;
import static zin.rashidi.springdatafilterquery.user.UserTestHelper.inactiveUser;

/**
 * @author Rashidi Zin
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserResourceTests {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Return result should contain only active users")
    void browse() {
        repository.saveAll(List.of(activeUser(), inactiveUser()));

        ResponseEntity<Users> response = restTemplate.getForEntity("/users", Users.class);
        Users users = response.getBody();

        assertThat(users)
                .hasSize(1)
                .extracting(User::getStatus)
                .containsOnly(ACTIVE);
    }

    static class Users extends ArrayList<User> {
        private static final long serialVersionUID = 2937275243381115269L;
    }
}
