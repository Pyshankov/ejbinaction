package ejb3inaction.example.pyshankov.Bean.BankService;

import ejb3inaction.example.pyshankov.persistence.BankService.BankAccount;
import ejb3inaction.example.pyshankov.persistence.BankService.CardInfo;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionReport;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionStatus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pyshankov on 08.11.15.
 */
@Stateless(name="BankAccountBeanRemote")
public class BankAccountBean implements BankAccountEAO {

   private static final EntityManagerFactory emf=Persistence.createEntityManagerFactory("bank-account");
    private  EntityManager em ;


    @PostConstruct
    public void initialize() {
        em =  emf.createEntityManager();
        //also can use COMMIT when we want to control synchronization with database by yourself(while transactions commits)
//        em.setFlushMode(FlushModeType.AUTO);
    }
    @PreDestroy
    public void cleanup() {
        if (em.isOpen()) {
            em.close();
            emf.close();
            em=null;
        }
    }
    //work
    public BankAccount addBankAccount(String name,CardInfo cardInfo, BigDecimal amount){
        BankAccount a=null;
        EntityTransaction et = em.getTransaction();
        try {
                et.begin();

                 a = new BankAccount(name, cardInfo, amount, null);
                em.persist(a);
              et.commit();
            }
      catch (Exception e){
            e.printStackTrace();
          em.getTransaction().rollback();
        }

        return  a;
    }
    //work
    public BankAccount addBankAccount(BankAccount ba) {

        return  addBankAccount(ba.getName(),ba.getCardInfo(),ba.getAmount());
    }
    //work
    public void dropAccount(long id) {
        em.getTransaction().begin();

        em.remove(em.find(BankAccount.class,id));
        em.getTransaction().commit();
    }
    //work
    public BankAccount updateAccount( BankAccount ba) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(ba);
           et.commit();
        return ba;
    }
    //work
    public BankAccount getAccount(long id) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        BankAccount ba =  em.find(BankAccount.class, id);
        et.commit();
        return ba;
    }
    //work
    public List<BankAccount> getListAccount() {

       List<BankAccount> list=null;
        EntityTransaction et = em.getTransaction();
        et.begin();
       list = em.createNamedQuery("getAllAccount").getResultList();
        et.commit();
        return list;
    }

    //work
    public  BankAccount addToAccount(final String toBankAccountNumber, final BigDecimal quantity) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        Query q = em.createNamedQuery("getByAccount").setParameter(1,toBankAccountNumber);
        BankAccount ba =(BankAccount)q.getSingleResult();
        ba.setAmount(ba.getAmount().add(quantity));
        em.merge(ba);
        et.commit();
        return ba;
    }
    //work
    public void addTransactionReport(TransactionReport transactionReport,long id) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        BankAccount ba = em.find(BankAccount.class, id);
        transactionReport.setBankAccount(ba);
        ba.getTransactionReports().add(transactionReport);
        et.commit();
    }
    //work
    public BankAccount getAccountByNumber( final String bankAccountNumber){
        EntityTransaction et = em.getTransaction();
        BankAccount ba = null;
        et.begin();

        Query query = em.createNamedQuery("getByAccount");
        query.setParameter(1,bankAccountNumber);
        ba = (BankAccount)query.getSingleResult();
        et.commit();
        return ba;

    }
}
