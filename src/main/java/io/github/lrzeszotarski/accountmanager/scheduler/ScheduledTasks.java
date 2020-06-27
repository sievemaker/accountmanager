package io.github.lrzeszotarski.accountmanager.scheduler;

import io.github.lrzeszotarski.accountmanager.domain.service.EventCleanupService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final EventCleanupService eventCleanupService;

    public ScheduledTasks(EventCleanupService eventCleanupService) {
        this.eventCleanupService = eventCleanupService;
    }

    @Scheduled(fixedRateString = "${jobs.cleanupOutdatedEventsJobRate}")
    public void cleanupOutdatedEvents() {
        eventCleanupService.cleanupEvents();
    }
}
