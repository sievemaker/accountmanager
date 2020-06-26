package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface EventStatisticsService {
    EventStatistics updateStatistics(Account account, Event event);

    List<EventStatistics> getStatistics(UUID accountId, OffsetDateTime day);
}
