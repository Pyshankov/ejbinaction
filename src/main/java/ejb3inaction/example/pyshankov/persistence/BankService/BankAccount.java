package ejb3inaction.example.pyshankov.persistence.BankService;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pyshankov on 07.11.15.
 */
/**

**/
@Entity
@NamedQueries({
@NamedQuery(
        name = "getByAccount",
        query = "SELECT c from BankAccount c where c.cardInfo.bankAccountNumber =?1"
),
        @NamedQuery(
                name = "getAllAccount",
                query = "SELECT c from BankAccount c"
        )
})
@Table(name = "BankAccount")
public class BankAccount implements Serializable {
    @Id
    @SequenceGenerator(name="BankAccount_idAccount_seq", sequenceName="BankAccount_idAccount_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="BankAccount_idAccount_seq")
    @Column(name = "idAccount",unique = true,nullable = false)
   private long idAccount;
    @Column(name = "name")
   private String name;
    @Embedded
   private CardInfo cardInfo;
    @Column(name = "amount")
    private BigDecimal amount;
    //define one-to-many unidirectional relation to TransactionReport,use cascade to delete all transactionReports associate with this account
    //EAGER - retrieving while first selecting from DB,LAZY - while fist getter invocation 
    @OneToMany(mappedBy = "bankAccount",cascade = {CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.LAZY) //default value LAZY for OneToMany
//    @JoinColumn(name="idBankAccount" ) //defines column,that will use to map TransactionReport
//    @JoinTable(
//            name = "TransactionReport",
//            joinColumns = @JoinColumn(name = "idBankAccount")
//    )
    private List<TransactionReport> transactionReports;

    public BankAccount(){};


    public long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(long idAccount) {
        this.idAccount = idAccount;
    }

    public String getName() {
        return name;
    }

    public BankAccount(String name, CardInfo cardInfo, BigDecimal amount, List<TransactionReport> transactionReports) {
        this.name = name;
        this.cardInfo = cardInfo;
        this.amount = amount;
        this.transactionReports = transactionReports;
    }

    public BigDecimal getAmount() {

        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }



    public List<TransactionReport> getTransactionReports() {
        return transactionReports;
    }

    public void setTransactionReports(List<TransactionReport> transactionReports) {
        this.transactionReports = transactionReports;
    }
}
