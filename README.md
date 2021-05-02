# Spring Data: Implement Repository Level Filter

Guide to implement query filter at repository level.

## Scenario

```gherkin
Given there are ACTIVE and INACTIVE users
When I get all users
Then I will only see ACTIVE users
```

## Verification

We will verify through integration test with help of `@SpringBootTest`

```java
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
```

## Implementation

### Entity

[User](src/main/java/zin/rashidi/springdatafilterquery/user/User.java) class contains information about `username` and
`status`.

```java
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private UserStatus status;

}
```

Implemented filter will be utilising `status` field.

### Filtered Query

[UserFilteredQueryRepository](src/main/java/zin/rashidi/springdatafilterquery/user/UserFilteredQueryRepository.java)
will be responsible to filter only `ACTIVE` users will be returned.

```java
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
```

### Repository Configuration

Finally, we will need to inform Spring Data about our custom `Repository`. This is implemented in
[UserRepositoryConfiguration](src/main/java/zin/rashidi/springdatafilterquery/user/UserRepositoryConfiguration.java).

```java
@Configuration
@EnableJpaRepositories(repositoryBaseClass = UserFilteredQueryRepository.class)
public class UserRepositoryConfiguration {
}
```

## Perform Verification

Once completed, execute [UserResourceTests](src/test/java/zin/rashidi/springdatafilterquery/user/UserResourceTests.java)
and `browse()` should validate that there is only one user being returned with `status` of `ACTIVE`.

### Repository Verification

Additionally, we can also perform repository level verification with `@DataJpaTest`

```java
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
```

The implementation can be found
in [UserRepositoryTests](src/test/java/zin/rashidi/springdatafilterquery/user/UserRepositoryTests.java).
