package net.intelie.challenge.infrastructure.repository;

import net.intelie.challenge.domain.abstractions.repository.EventStore;
import net.intelie.challenge.domain.abstractions.service.EventIterator;
import net.intelie.challenge.domain.models.Event;
import net.intelie.challenge.domain.service.EventIteratorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventStoreImpl implements EventStore {
    private final List<Event> events;

    public EventStoreImpl() {
        // I was thinking about using a thread safe such as CopyOnWriteArrayList,
        // but it could end up harming the system's performance, since it takes this array and creates another one and,
        // depending on the scenario, this can generate a large cost in processing.
        this.events = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized void insert(Event event) {
        if (event == null) {
            throw new NullPointerException("Event cannot be null.");
        } else if (event.type() == null || event.timestamp() < 0) {
            throw new NullPointerException("Event type or timestamp cannot be null.");
        }

        events.add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        if (type == null) {
            throw new NullPointerException("Event type cannot be null,");
        }
        // A filter based on the predicate is used where the type is equal to the type that was passed in the parameter.
        List<Event> eventList = events.parallelStream().filter(p -> p.type().equals(type)).collect(Collectors.toList());
        events.removeAll(eventList);
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        // A validation was done checking the type, startTime and endTime.
        // If none of them satisfy the validation, it triggers an error.

        if (type == null) {
            throw new NullPointerException("Type cannot be null");
        } else if (startTime < 0 || endTime == 0) {
            throw new IllegalStateException("Start Time cannot be less than zero and End Time cannot be zero.");
        }

        Predicate<Event> predicate = p -> p.type().equals(type) && (p.timestamp() >= startTime) && (p.timestamp() < endTime);
        return new EventIteratorImpl(events, predicate);
    }
}
