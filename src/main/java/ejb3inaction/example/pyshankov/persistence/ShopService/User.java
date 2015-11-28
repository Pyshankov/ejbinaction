package ejb3inaction.example.pyshankov.persistence.ShopService;

import javax.persistence.*;

/**
 * Created by pyshankov on 07.11.15.
 */
//we will use single table strategy
// @Entity
@Entity
@Table(name = "users")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE",discriminatorType = DiscriminatorType.STRING,length = 1)
@DiscriminatorValue(value="U")
public class User {
    @Id
    @SequenceGenerator(name="items_idItem_seq", sequenceName="items_idItem_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="items_idItem_seq")
   private int id;

    private String name;
    private String password;
    private String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public User(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
