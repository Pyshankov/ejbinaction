package ejb3inaction.example.Simple;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by pyshankov on 05.11.15.
 */
@MessageDriven(
        //имя topic, на который подписан бин
        mappedName="jms/TutorialTopic",
        name = "ExampleMDB",
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName="destinationType",
                        propertyValue="javax.jms.Topic")}
)
public class MDBExample implements MessageListener {

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String response;
    //метод, вызываемый при получении нового сообщения

    public void onMessage(Message msg) {
        try {
            TextMessage message = (TextMessage)msg;
            //считываем свойство из соответствующего поля, заданное вручную в consumer
            System.out.println("FROM MDB - client type IS " + message.getStringProperty("clientType"));
            //считываем  само сообщение
            setResponse(message.getText());
            System.out.println("FROM MDB - payload  IS" + message.getText());
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

}