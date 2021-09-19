package net.intelie.challenge;


import net.intelie.challenge.domain.abstractions.repository.EventStore;
import net.intelie.challenge.domain.models.Event;
import net.intelie.challenge.infrastructure.repository.EventStoreImpl;


public class Main {
    public static void main(String[] args) {
        Event event = new Event("Type1", 1L);
        Event event1 = new Event("Type2", 2L);
        Event event2 = new Event("Type3", 3L);
        EventStoreImpl store = new EventStoreImpl();

        store.insert(event);
        store.insert(event1);
        store.insert(event2);
        System.out.println(store.query("Type3", 0L, 3L));
    }
}
