package ejb3inaction.example.pyshankov.persistence.BankService;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by pyshankov on 07.11.15.
 *
 * declare embedded class for BankAccount
 */
@Embeddable
public class CardInfo implements Serializable {
    @Column(name = "creditCardExpiration")
    private java.sql.Date creditCardExpiration;
    @Column(name = "bankAccountNumber")
    private String bankAccountNumber;
    @Column(name = "bankName")
    private String bankName;
    @Column(name ="cardCode")
    private int cardCode;

    public CardInfo(){};

    public CardInfo(Date creditCardExpiration, String bankAccountNumber, String bankName, int cardCode) {
        this.creditCardExpiration = creditCardExpiration;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.cardCode = cardCode;
    }

    public Date getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(Date creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getCardCode() {
        return cardCode;
    }

    public void setCardCode(int cardCode) {
        this.cardCode = cardCode;
    }
}
