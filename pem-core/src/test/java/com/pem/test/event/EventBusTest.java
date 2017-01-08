package com.pem.test.event;

import com.pem.core.rx.event.BaseEvent;
import com.pem.core.rx.eventbus.EventBus;
import com.pem.core.rx.eventbus.RxEvenBus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventBusTest {

    private EventBus<BaseEvent> eventBus;

    @Before
    public void init() {
        eventBus = new RxEvenBus<>();
        eventBus.getObservable(TestEvent.class).subscribe(testEvent -> testEvent.setEventHandled(true));
    }

    @Test
    public void testFirstGreaterOperations() {
        TestEvent testEvent = new TestEvent();
        eventBus.post(testEvent);

        UnhandledEvent unhandledEvent = new UnhandledEvent();
        eventBus.post(unhandledEvent);

        Assert.assertTrue(testEvent.isEventHandled());
        Assert.assertFalse(unhandledEvent.isEventHandled());
    }

    private class UnhandledEvent extends BaseEvent<Void> {
        boolean eventHandled = false;

        public UnhandledEvent() {
            super(null);
        }

        public boolean isEventHandled() {
            return eventHandled;
        }

        public void setEventHandled(boolean eventHandled) {
            this.eventHandled = eventHandled;
        }
    }
}
