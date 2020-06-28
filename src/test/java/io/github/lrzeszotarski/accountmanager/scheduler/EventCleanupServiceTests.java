package io.github.lrzeszotarski.accountmanager.scheduler;

import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository;
import io.github.lrzeszotarski.accountmanager.domain.service.EventCleanupService;
import io.github.lrzeszotarski.accountmanager.domain.service.TimeService;
import io.github.lrzeszotarski.accountmanager.scheduler.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventCleanupServiceTests {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCleanupService eventCleanupService;

    @MockBean
    private TimeService timeService;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Value("${timeservice.eventValidityPeriodInDays}")
    private Integer eventValidityPeriodInDays;

    @Test
    public void testEventsCleanup() {
        final OffsetDateTime now = OffsetDateTime.now();

        given(timeService.getMinimumDateForValidEvents()).willReturn(now.minus(eventValidityPeriodInDays, ChronoUnit.DAYS));

        final Event eventOutdated1 = new Event();
        final UUID eventId1 = UUID.randomUUID();
        eventOutdated1.setEventId(eventId1);
        eventOutdated1.setHappenedAt(now.minus(31, ChronoUnit.DAYS));

        final Event eventOutdated2 = new Event();
        final UUID eventId2 = UUID.randomUUID();
        eventOutdated2.setEventId(eventId2);
        eventOutdated2.setHappenedAt(now.minus(30, ChronoUnit.DAYS).minus(1, ChronoUnit.MILLIS));

        final Event eventValid1 = new Event();
        final UUID eventId3 = UUID.randomUUID();
        eventValid1.setEventId(eventId3);
        eventValid1.setHappenedAt(now.minus(29, ChronoUnit.DAYS));

        final Event eventValid2 = new Event();
        UUID eventId4 = UUID.randomUUID();
        eventValid2.setEventId(eventId4);
        eventValid2.setHappenedAt(now.minus(28, ChronoUnit.DAYS));

        eventRepository.saveAll(Stream.of(eventOutdated1, eventOutdated2, eventValid1, eventValid2).collect(Collectors.toList()));

        final List<Event> all = eventRepository.findAll();
        assertEquals(all.size(), 4);
        eventCleanupService.cleanupEvents();
        final List<Event> afterCleanUp = eventRepository.findAll();
        assertEquals(afterCleanUp.size(), 2);
        final List<UUID> validUUIDs = afterCleanUp.stream().map(Event::getEventId).collect(Collectors.toList());
        assertTrue(validUUIDs.contains(eventId3));
        assertTrue(validUUIDs.contains(eventId4));
    }
}
