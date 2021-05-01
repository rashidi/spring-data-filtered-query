package zin.rashidi.springdatafilterquery.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Rashidi Zin
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private UserStatus status;

}
