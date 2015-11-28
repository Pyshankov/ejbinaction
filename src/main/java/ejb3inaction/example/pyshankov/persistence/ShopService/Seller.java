package ejb3inaction.example.pyshankov.persistence.ShopService;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by pyshankov on 11.11.15.
 */
@Entity
@DiscriminatorValue(value="S")
public class Seller extends User {
    @OneToMany
    private List<Item> listItems;
}
