package ejb3inaction.example.pyshankov.Bean.BankService;

import ejb3inaction.example.pyshankov.persistence.BankService.BankAccount;
import ejb3inaction.example.pyshankov.persistence.BankService.CardInfo;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionReport;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyshankov on 07.11.15.
 *
 */
@Remote
public interface BankAccountEAO {
    BankAccount addBankAccount(String name,CardInfo cardInfo, BigDecimal amount) throws SQLException;
    BankAccount addBankAccount(BankAccount ba);

    void dropAccount(long id);

    BankAccount updateAccount( BankAccount ba);

    BankAccount getAccount(long id);

    List<BankAccount> getListAccount();


    BankAccount addToAccount(final String toBankAccountNumber,final BigDecimal quantity);

    void addTransactionReport(TransactionReport transactionReport,long id);
    public BankAccount getAccountByNumber(final String bankAccountNumber);
}