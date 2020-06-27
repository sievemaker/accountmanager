package io.github.lrzeszotarski.accountmanager.domain.repository;

import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventId(UUID eventId);

    long deleteByHappenedAt(OffsetDateTime day);
}
