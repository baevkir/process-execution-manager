package com.pem.test.event;

import com.pem.integration.provider.PemServiceProviderImpl;
import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.*;
import com.pem.model.operation.common.OperationDTO;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationEventsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PemServiceProviderImpl.class);

    @Autowired
    private ServiceEventBus eventBus;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        CreateOperationEvent event = new CreateOperationEvent(creator.createSimpleBeanOperation());
        TestObserver<OperationDTO> testObserver  = event.getSingle().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
    }

    @Test
    public void testUpdateOperation() {
        UpdateOperationEvent event = new UpdateOperationEvent(creator.createSimpleBeanOperation());
        TestObserver<Void> testObserver  = event.getCompletable().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    public void testDeleteOperation() {
        DeleteOperationEvent event = new DeleteOperationEvent(BigInteger.ONE);
        TestObserver<Void> testObserver  = event.getCompletable().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    public void testGetOperation() {
        GetOperationEvent event = new GetOperationEvent(BigInteger.ONE);
        TestObserver<OperationDTO> testObserver  = event.getSingle().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
    }

    @Test
    public void testGetAllOperation() {
        GetOperationListEvent event = new GetOperationListEvent();
        TestObserver<OperationDTO> testObserver  = event.getObservable().test();
        eventBus.post(event);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(10);
    }
}
