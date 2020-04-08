package com.test.ejb;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.test.handler.ExampleMessageHandler;
import com.test.handler.impl.ExampleMessageHandlerUntestable;

@MessageDriven(mappedName = "jms/queue/example", activationConfig = {
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/example")
})
public class ExampleMDB implements MessageListener {

    private ExampleMessageHandler exampleMessageHandler = new ExampleMessageHandlerUntestable();

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                exampleMessageHandler.doSomething(textMessage.getText());
            } catch (JMSException e) {
                throw new RuntimeException("That was unexpected!", e);
            }
        }

    }
}
