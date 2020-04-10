package com.test.ejb;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.test.handler.ExampleMessageHandler;
import com.test.handler.impl.ExampleMessageHandlerUntestable;

import entity.InventoryCategory;
@TransactionManagement(TransactionManagementType.CONTAINER)
@MessageDriven(mappedName = "jms/queue/example", activationConfig = {
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/example")
})
public class ExampleMDB implements MessageListener {
	
	 @EJB
	 private CrudService crudSvc;

    private ExampleMessageHandler exampleMessageHandler = new ExampleMessageHandlerUntestable();

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            InventoryCategory ic = new InventoryCategory();
        	ic.setCategoryDescription("descriptionMDB");
        	ic.setCategoryName("nameMDB");
        	ic.setVersion(15);
        	crudSvc.createNewTransaction(ic);
            try {
                exampleMessageHandler.doSomething(textMessage.getText());
            } catch (JMSException e) {
                throw new RuntimeException("That was unexpected!", e);
            }
        }

    }
}
