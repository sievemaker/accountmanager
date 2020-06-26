package io.github.lrzeszotarski.accountmanager.domain.repository;

import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatisticsRepository extends JpaRepository<EventStatistics, Long> {
}
