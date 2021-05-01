package zin.rashidi.springdatafilterquery.user;

import static zin.rashidi.springdatafilterquery.user.UserStatus.ACTIVE;
import static zin.rashidi.springdatafilterquery.user.UserStatus.INACTIVE;

/**
 * @author Rashidi Zin
 */
public class UserTestHelper {

    private UserTestHelper() {
    }

    static User activeUser() {
        return create("active.user", ACTIVE);
    }

    static User inactiveUser() {
        return create("inactive.user", INACTIVE);
    }

    private static User create(String username, UserStatus status) {
        User user = new User();

        user.setUsername(username);
        user.setStatus(status);

        return user;
    }

}
