package com.pem.test.event;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.process.event.*;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProcessEventTest {
    @Autowired
    private ServiceEventBus eventBus;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        CreateProcessEvent event = new CreateProcessEvent(creator.createSimpleBeanOperation());
        TestObserver<ExecutionProcessDTO> testObserver  = event.getSingle().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
    }

    @Test
    public void testUpdateEvent() {
        UpdateProcessEvent event = new UpdateProcessEvent(new ExecutionProcessDTO());
        TestObserver<Void> testObserver  = event.getCompletable().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    public void testExecuteEvent() {
        ExecuteProcessEvent event = new ExecuteProcessEvent(new ExecutionProcessDTO());
        TestObserver<Void> testObserver  = event.getCompletable().test();
        eventBus.post(event);

//        testSubscriber.assertCompleted();
//        testSubscriber.assertNoErrors();
//        testSubscriber.assertNoValues();
    }

    @Test
    public void testGetOneEvent() {
        GetOneProcessEvent event = new GetOneProcessEvent(BigInteger.ONE);
        TestObserver<ExecutionProcessDTO> testObserver  = event.getSingle().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
    }

    @Test
    public void testProcessListEvent() {
        GetProcessListEvent event = new GetProcessListEvent();
        TestObserver<ExecutionProcessDTO> testObserver  = event.getObservable().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(10);
    }
}
