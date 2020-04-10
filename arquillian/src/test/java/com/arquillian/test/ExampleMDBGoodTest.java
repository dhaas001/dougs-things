package com.arquillian.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.test.ejb.CrudService;
import com.test.ejb.ExampleMDB;
import com.test.ejb.impl.CrudServiceBean;
import com.test.handler.ExampleMessageHandler;
import com.test.handler.impl.ExampleMessageHandlerUntestable;

import entity.InventoryCategory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class ExampleMDBGoodTest {

    @Resource(mappedName = "ConnectionFactory", name = "ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/queue/example", name = "jms/queue/example")
    private Queue queue;

    @Inject
    private ExampleMessageHandler exampleMessageHandler;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "exampleMDB.war")
        		.addClasses(ExampleMDB.class, ExampleMessageHandler.class, ExampleMessageHandlerUntestable.class, 
        				CrudService.class, CrudServiceBean.class, InventoryCategory.class, ExampleMessageHandlerTestable.class, ExampleMessageHandlerUntestable.class)
//                .addPackages(true, ExampleMDB.class.getPackage(), ExampleMessageHandler.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
//                .addAsWebInfResource("hornetq-jms.xml", "hornetq-jms.xml")
                .addAsWebInfResource("beans-alternative.xml", "beans.xml");
        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    @InSequence(1)
    public void testOnMessage() throws Exception {
    	assertNotNull("exampleMessageHandler is null", exampleMessageHandler);
 
//    	ExampleMessageHandler exampleMessageHandler = new ExampleMessageHandlerTestable();
    	assertNotNull("queue is null", queue);
    	System.out.println("queue good");
    	assertNotNull("connectionFactory is null", connectionFactory);
    	System.out.println("connection factory good");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);

        TextMessage textMessage = session.createTextMessage("Hello world!");
        
        producer.send(textMessage);
        System.out.println("sent message: waiting for handling");
        Thread.currentThread().sleep(5000);
    	System.out.println("Done sleeping");
        session.close();
        connection.close();

        // We cast to our configured handler defined in beans.xml
        ExampleMessageHandlerTestable testHandler = (ExampleMessageHandlerTestable) exampleMessageHandler;
//        assertThat(testHandler.poll(10), is("Hello world!"));
    }

}
