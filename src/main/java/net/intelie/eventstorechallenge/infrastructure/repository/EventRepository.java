package net.intelie.eventstorechallenge.infrastructure.repository;

import net.intelie.eventstorechallenge.core.domain.abstractions.repository.EventStore;
import net.intelie.eventstorechallenge.core.domain.abstractions.service.EventIterator;
import net.intelie.eventstorechallenge.core.domain.models.Event;

public class EventRepository implements EventStore {
    @Override
    public void insert(Event event) {

    }

    @Override
    public void removeAll(String type) {

    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        return null;
    }
}
