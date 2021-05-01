package zin.rashidi.springdatafilterquery.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static zin.rashidi.springdatafilterquery.user.UserStatus.INACTIVE;

/**
 * @author Rashidi Zin
 */
@RestController
@AllArgsConstructor
public class UserResource {

    private final UserRepository repository;

    @GetMapping("/users")
    public List<User> browse() {
        return repository.findByStatusIsNot(INACTIVE);
    }

}
