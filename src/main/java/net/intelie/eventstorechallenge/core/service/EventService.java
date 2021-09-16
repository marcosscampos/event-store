package net.intelie.eventstorechallenge.core.service;

import lombok.RequiredArgsConstructor;
import net.intelie.eventstorechallenge.core.domain.abstractions.service.EventIterator;
import net.intelie.eventstorechallenge.core.domain.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EventService implements EventIterator {
    @Override
    public boolean moveNext() {
        return false;
    }

    @Override
    public Event current() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void close() throws Exception {

    }
}
