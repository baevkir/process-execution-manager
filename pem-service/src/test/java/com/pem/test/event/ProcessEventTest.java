package com.pem.test.event;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.process.event.*;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.observers.TestSubscriber;

import java.math.BigInteger;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProcessEventTest {
    @Autowired
    private ServiceEventBus eventBus;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        TestSubscriber<ExecutionProcessDTO> testSubscriber = new TestSubscriber<>();
        CreateProcessEvent event = new CreateProcessEvent(creator.createSimpleBeanOperation());
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testUpdateEvent() {
        TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        UpdateProcessEvent event = new UpdateProcessEvent(new ExecutionProcessDTO());
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNoValues();
    }

    @Test
    public void testExecuteEvent() {
        TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        ExecuteProcessEvent event = new ExecuteProcessEvent(new ExecutionProcessDTO());
        event.setObserver(testSubscriber);
        eventBus.post(event);

//        testSubscriber.assertCompleted();
//        testSubscriber.assertNoErrors();
//        testSubscriber.assertNoValues();
    }

    @Test
    public void testGetOneEvent() {
        TestSubscriber<ExecutionProcessDTO> testSubscriber = new TestSubscriber<>();
        GetOneProcessEvent event = new GetOneProcessEvent(BigInteger.ONE);
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testProcessListEvent() {
        TestSubscriber<List<ExecutionProcessDTO>> testSubscriber = new TestSubscriber<>();
        GetProcessListEvent event = new GetProcessListEvent();
        event.setObserver(testSubscriber);
        eventBus.post(event);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
    }
}
