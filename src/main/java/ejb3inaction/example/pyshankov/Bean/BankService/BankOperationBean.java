package ejb3inaction.example.pyshankov.Bean.BankService;

import ejb3inaction.example.pyshankov.persistence.BankService.BankAccount;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionReport;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionStatus;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by pyshankov on 12.11.15.
 */
@Stateless(name = "BankOperationBeanRemote")
@TransactionManagement(TransactionManagementType.BEAN)
public class BankOperationBean implements BankOperationEAO {

    @EJB(name = "BankAccountBeanRemote")   //getting EJB via DI
    private BankAccountEAO bankAccountEAO;
    @Resource
    private UserTransaction userTransaction;


    public void sentMoney(BigDecimal quantity, BankAccount yourBankAccount, String toBankAccountNumber) {
        try {
            userTransaction.begin();
            java.util.Date date= new java.util.Date();
            if(yourBankAccount.getAmount().compareTo(quantity)>=0){
                BankAccount baTo=bankAccountEAO.getAccountByNumber(toBankAccountNumber);
                baTo.setAmount(baTo.getAmount().add(quantity));
                yourBankAccount.setAmount(yourBankAccount.getAmount().subtract(quantity));
                bankAccountEAO.updateAccount(baTo);
                bankAccountEAO.updateAccount(yourBankAccount);
                bankAccountEAO.addTransactionReport(new TransactionReport(toBankAccountNumber,
                        TransactionStatus.COMPLETED,new Timestamp(date.getTime()))
                        ,yourBankAccount.getIdAccount());
            }
            else throw new BankException();
            userTransaction.commit();
        }catch (BankException e){
            try {
                userTransaction.rollback();
                java.util.Date date= new java.util.Date();
                bankAccountEAO.addTransactionReport(
                        new TransactionReport(toBankAccountNumber,
                                TransactionStatus.NOT_COMPLETED,
                                new Timestamp(date.getTime())),
                        yourBankAccount.getIdAccount());
                e.printStackTrace();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
