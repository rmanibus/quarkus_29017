package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

@ApplicationScoped
public class Engine {

    @Inject
    Event<SomeEvent> event;
    @Inject
    Context context;
    public void execute(String value){
        event.fireAsync(new SomeEvent(value));
    }


    @ActivateRequestContext
    void dequeueProcessingRequests(@ObservesAsync SomeEvent event) {
        context.set(event.value);
        before = context.get();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        after = context.get();
        System.out.println("result: " + before);
        System.out.println("result: " + after);

        synchronized (lock){
            done = true;
            lock.notify();
        }
    }


    static boolean done = false;
    static final Object lock = new Object();

    static String before;
    static String after;
}