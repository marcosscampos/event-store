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
        if(this.events == null) {
            synchronized (this) {
                events = new ArrayList<>();
            }
        }

        events.add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        List<Event> eventList = events.parallelStream().filter(p -> p.type().equals(type)).collect(Collectors.toList());
        events.removeAll(eventList);
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        Predicate<Event> predicate = p -> p.type().equals(type) && (p.timestamp() >= startTime) && (p.timestamp() < endTime);

        return new EventIteratorImpl(events, predicate);
    }
}
