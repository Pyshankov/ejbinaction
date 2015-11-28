package ejb3inaction.example.pyshankov.persistence.BankService;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by pyshankov on 07.11.15.
 *
 * use sequence generate type for generating id (also exist : IDENTITY,TABLE,AUTO - JPA creates generator for table)
 * use field persistence
 * TransactionReport with BankAccount declares ManyToOne relationship
 */

/**


**/
@Entity
@Table(name ="TransactionReport")
public class TransactionReport implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="TransactionReport_idReport_seq", sequenceName="TransactionReport_idReport_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="TransactionReport_idReport_seq")
    @Column(name = "idReport",unique = true,nullable = false)
    private long idReport;

    @Column(name = "forwardToAccount")
    private String forwardToAccount;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    //so,it's redundant to use @Temporal annotation because we use types from java.sql.* to define time
    @Column(name = "transactionTime")
    private java.sql.Timestamp transactionTime;
    @ManyToOne
    @JoinColumn(name="idbankaccount",
            referencedColumnName="idAccount")
    private BankAccount bankAccount;
    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public long getIdReport() {
        return idReport;
    }

    public void setIdReport(long idReport) {
        this.idReport = idReport;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public TransactionReport(String forwardToAccount, TransactionStatus status, Timestamp transactionTime) {
        this.forwardToAccount = forwardToAccount;
        this.status = status;
        this.transactionTime = transactionTime;
    }
   public TransactionReport(){};
    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getForwardToAccount() {
        return forwardToAccount;
    }

    public void setForwardToAccount(String forwardToAccount) {
        this.forwardToAccount = forwardToAccount;
    }
}
