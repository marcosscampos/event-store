package net.intelie.challenge.infrastructure.repository;

import net.intelie.challenge.domain.abstractions.repository.EventStore;
import net.intelie.challenge.domain.abstractions.service.EventIterator;
import net.intelie.challenge.domain.models.Event;
import net.intelie.challenge.domain.service.EventIteratorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventStoreImpl implements EventStore {
    private ArrayList<Event> events;

    public EventStoreImpl() {
        this.events = null;
    }

    @Override
    public synchronized void insert(Event event) {
        if (event == null) {
            throw new NullPointerException("Event cannot be null.");
        } else if (event.type() == null || event.timestamp() < 0) {
            throw new NullPointerException("Event type or timestamp cannot be null.");
        }

        if (this.events == null) {
            synchronized (this) {
                events = new ArrayList<>();
            }
        }

        events.add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        if (type == null) {
            throw new NullPointerException("Event type cannot be null,");
        }

        List<Event> eventList = events.parallelStream().filter(p -> p.type().equals(type)).collect(Collectors.toList());
        events.removeAll(eventList);
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        if (type == null) {
            throw new NullPointerException("Type cannot be null");
        } else if (startTime < 0 || endTime == 0) {
            throw new IllegalStateException("Start Time cannot be less than zero and End Time cannot be zero.");
        }

        Predicate<Event> predicate = p -> p.type().equals(type) && (p.timestamp() >= startTime) && (p.timestamp() < endTime);
        return new EventIteratorImpl(events, predicate);
    }
}
