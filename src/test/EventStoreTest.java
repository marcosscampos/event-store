package test;

import net.intelie.challenge.domain.abstractions.repository.EventStore;
import net.intelie.challenge.domain.abstractions.service.EventIterator;
import net.intelie.challenge.domain.models.Event;
import net.intelie.challenge.infrastructure.repository.EventStoreImpl;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventStoreTest {
    EventStore store = new EventStoreImpl();

    @Test
    public void TryToInsertWithTypeNull() {
        Event event = new Event(null, 0);
        Exception exception = assertThrows(NullPointerException.class, () -> {
            store.insert(event);
        });

        String expectedMessage = "Event type or timestamp cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void InsertEvent() {
        store.insert(new Event("TestEvent1", 2L));
        EventIterator iterator = store.query("TestEvent1", 0L, 2L);
        assertFalse(iterator.moveNext());
    }

    @Test
    public void RemoveAllEvents() {
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event("Event0", 0L));
        events.add(new Event("Event0", 1L));
        events.add(new Event("Event1", 2L));
        events.add(new Event("Event2", 3L));
        events.add(new Event("Event3", 4L));
        events.add(new Event("Event4", 5L));

        for(Event item : events) {
            store.insert(item);
        }
        store.removeAll("Event0");
        EventIterator iterator = store.query("Event0", 0L, 1L);
        assertFalse(iterator.moveNext());
    }
}
