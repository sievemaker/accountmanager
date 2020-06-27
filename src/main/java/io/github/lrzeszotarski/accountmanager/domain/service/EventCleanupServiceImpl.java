package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EventCleanupServiceImpl implements EventCleanupService {

    private final EventRepository eventRepository;

    private final TimeService timeService;

    public EventCleanupServiceImpl(EventRepository eventRepository, TimeService timeService) {
        this.eventRepository = eventRepository;
        this.timeService = timeService;
    }

    @Override
    public long cleanupEvents() {
        return eventRepository.deleteByHappenedAt(timeService.get30DaysAgoDate());
    }
}
