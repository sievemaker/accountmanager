package io.github.lrzeszotarski.accountmanager.domain.repository;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface EventStatisticsRepository extends JpaRepository<EventStatistics, Long> {
    EventStatistics findByTypeAndAccountAndDay(String type, Account account, OffsetDateTime day);

    List<EventStatistics> findAllByAccountAndDay(Account account, OffsetDateTime day);
}
