package ejb3inaction.example.pyshankov.Bean.BankService;

import ejb3inaction.example.pyshankov.persistence.BankService.BankAccount;

import javax.ejb.Remote;
import java.math.BigDecimal;

/**
 * Created by pyshankov on 12.11.15.
 */
@Remote
public interface BankOperationEAO {
   void sentMoney(BigDecimal quantity, BankAccount yourBankAccount, String toBankAccountNumber);
}
