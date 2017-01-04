package com.pem.test.event;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.*;
import com.pem.model.operation.common.OperationDTO;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.observers.TestSubscriber;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationEventsTest {

    @Autowired
    private ServiceEventBus eventBus;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        TestSubscriber<OperationDTO> testSubscriber = new TestSubscriber<>();
        CreateOperationEvent event = new CreateOperationEvent(creator.createSimpleBeanOperation());
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testUpdateOperation() {
        TestSubscriber<OperationDTO> testSubscriber = new TestSubscriber<>();
        UpdateOperationEvent event = new UpdateOperationEvent(creator.createSimpleBeanOperation());
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNoValues();
    }

    @Test
    public void testDeleteOperation() {
        TestSubscriber<OperationDTO> testSubscriber = new TestSubscriber<>();
        DeleteOperationEvent event = new DeleteOperationEvent(BigInteger.ONE);
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNoValues();
    }

    @Test
    public void testGetOperation() {
        TestSubscriber<OperationDTO> testSubscriber = new TestSubscriber<>();
        GetOperationEvent event = new GetOperationEvent(BigInteger.ONE);
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testGetAllOperation() {
        TestSubscriber<OperationDTO> testSubscriber = new TestSubscriber<>();
        GetAllOperationsEvent event = new GetAllOperationsEvent();
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
    }
}
