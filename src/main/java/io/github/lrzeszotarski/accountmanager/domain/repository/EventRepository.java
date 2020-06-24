package io.github.lrzeszotarski.accountmanager.domain.repository;

import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
