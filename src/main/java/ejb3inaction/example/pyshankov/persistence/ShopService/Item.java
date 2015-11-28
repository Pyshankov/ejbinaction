package ejb3inaction.example.pyshankov.persistence.ShopService;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by pyshankov on 13.11.15.
 */
@Entity
@Table(name = "item")
public class Item {
    @Id
    @SequenceGenerator(name="items_idItem_seq", sequenceName="items_idItem_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="items_idItem_seq")
    @Column(name = "idItem",unique = true,nullable = false)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private BigDecimal cost;

}
