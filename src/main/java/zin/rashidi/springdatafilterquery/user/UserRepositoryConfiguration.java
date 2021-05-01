package zin.rashidi.springdatafilterquery.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Rashidi Zin
 */
@Configuration
@EnableJpaRepositories(repositoryBaseClass = UserFilteredQueryRepository.class)
public class UserRepositoryConfiguration {
}
