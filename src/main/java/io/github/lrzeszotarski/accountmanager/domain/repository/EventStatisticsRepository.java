package io.github.lrzeszotarski.accountmanager.domain.repository;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface EventStatisticsRepository extends JpaRepository<EventStatistics, Long> {
    EventStatistics findByTypeAndAccountAndHappenedAt(String type, Account account, OffsetDateTime happenedAt);
}
