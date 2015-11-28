import ejb3inaction.example.Simple.Counter;
import ejb3inaction.example.pyshankov.Bean.BankService.BankAccountEAO;
import ejb3inaction.example.pyshankov.Bean.BankService.BankOperationEAO;
import ejb3inaction.example.pyshankov.persistence.BankService.BankAccount;
import ejb3inaction.example.pyshankov.persistence.BankService.CardInfo;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionReport;
import ejb3inaction.example.pyshankov.persistence.BankService.TransactionStatus;


import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyshankov on 01.11.15.
 */

public class servlet extends HttpServlet {
    @Resource(name="jms/TutorialPool")
    private ConnectionFactory connectionFactory;

    @Resource(name="jms/TutorialTopic")
    private Destination destination;



    @EJB
    private Counter counter;
    @EJB(name = "BankAccountBeanRemote")   //getting EJB via DI
    private BankAccountEAO bankAccountEAO;
    @EJB(name = "BankOperationBeanRemote")
    private BankOperationEAO bankOperationEAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        counter.increment();
        if(counter.count()==20) {
            counter.reset();
        }
        sendString(" first my jms topic! it's something beautiful!");
        req.setAttribute("counter",counter.count());

        java.sql.Date eDate = new java.sql.Date(20150412);
        BankAccount bankAccount = new BankAccount("Vasilii Pupkin1",new CardInfo(eDate,"2134_5478_3434","PrivatBank1",23671), BigDecimal.valueOf(10000),null);
        bankOperationEAO.sentMoney(new BigDecimal(1004003000),bankAccountEAO.getAccount(4),bankAccountEAO.getAccount(9).getCardInfo().getBankAccountNumber());
//        try {
//            bankAccountEAO.addBankAccount(bankAccount.getName(),bankAccount.getCardInfo(),bankAccount.getAmount());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        req.setAttribute("bla", bankAccountEAO.getAccount(5));
//        bankAccountEAO.dropAccount(5);
           // bankAccountEAO.addBankAccount(bankAccount);
        bankAccountEAO.addToAccount("1234_5678_1012",new BigDecimal(100045455));
         req.setAttribute("bla1", bankAccountEAO.getAccountByNumber("1234_5678_1012"));
        //BankAccount a7 = bankAccountEAO.getAccount(7);
       // a7.setAmount(new BigDecimal(1000000000));
       // java.util.Date date= new java.util.Date();
//        bankAccountEAO.addTransactionReport(new TransactionReport("bla", TransactionStatus.COMPLETED,new Timestamp(date.getTime())),a7.getIdAccount());
//            bankAccountEAO.updateAccount(a7);
//            bankAccountEAO.dropAccount(a7.getIdAccount());
        List<BankAccount> lba = bankAccountEAO.getListAccount();
        req.setAttribute("bla",lba.get(0));
        req.getRequestDispatcher("main.jsp").forward(req,resp);
    }


    public void sendString(String enterString) {
        try {
            //создаем подключение
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            //добавим в JMS сообщение собственное свойство в поле сообщения со свойствами
            message.setStringProperty("clientType", "web clien");
            //добавляем payload в сообщение
            message.setText(enterString);
            //отправляем сообщение
            producer.send(message);
            System.out.println("message sent");
            //закрываем соединения
            session.close();
            connection.close();

        } catch (JMSException ex) {
            System.err.println("Sending message error");
            ex.printStackTrace();
        }
    }
}
