package com.pem.test.event;

import com.pem.core.rx.event.BaseEvent;
import com.pem.core.rx.event.ThrowableEvent;
import com.pem.core.rx.eventbus.EventBus;
import com.pem.core.rx.eventbus.RxEvenBus;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;

public class EventBusTest {

    private EventBus<BaseEvent> eventBus;

    @Before
    public void init() {
        eventBus = new RxEvenBus<>();
    }

    @Test
    public void testFirstGreaterOperations() {
        TestObserver<TestEvent> testEventObserver = eventBus.getObservable(TestEvent.class).test();
        TestEvent testEvent = new TestEvent();
        eventBus.post(testEvent);

        testEventObserver.assertNoErrors().assertValueCount(1).assertValue(testEvent);
    }

    @Test
    public void testErrorEvent() {
        RuntimeException testException = new RuntimeException("Test exception");

        TestObserver<Object> throwableObserver = eventBus.getObservable().map(baseEvent -> {
            throw testException;
        }).onErrorReturn(exception -> new ThrowableEvent(exception)).test();

        eventBus.getObservable(TestEvent.class).subscribe(testEvent -> {
            throw testException;
        });
        eventBus.post(new TestEvent());

        throwableObserver.assertNoErrors().assertValueCount(1).assertValue(object -> {
            if (!(object instanceof ThrowableEvent)) {
                return false;
            }

            ThrowableEvent event = (ThrowableEvent) object;

            return testException.equals(event.getThrowable());
        });

        TestObserver<TestEvent1> testEvent1Observer = eventBus.getObservable(TestEvent1.class).test();
        TestEvent1 testEvent1 = new TestEvent1();

        eventBus.post(testEvent1);

        testEvent1Observer.assertNoErrors().assertValueCount(1).assertValue(testEvent1);
    }

    private class TestEvent extends BaseEvent {
    }

    private class TestEvent1 extends BaseEvent {
    }

}
