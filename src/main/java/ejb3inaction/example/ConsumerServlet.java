package ejb3inaction.example;

import ejb3inaction.example.Simple.MDBExample;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pyshankov on 05.11.15.
 */
public class ConsumerServlet extends HttpServlet {

    @Resource(name="jms/TutorialPool")
    private ConnectionFactory connectionFactory;

    @Resource(name="jms/TutorialTopic")
    private Destination destination;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
           Connection connection = connectionFactory.createConnection();
           Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
           MessageConsumer consumer = session.createConsumer(destination);
           connection.start();

               Message msg = consumer.receive();
                req.setAttribute("MDB", ((TextMessage)msg).getText());


           connection.close();
       }catch (JMSException e){e.printStackTrace();}



    }
}
