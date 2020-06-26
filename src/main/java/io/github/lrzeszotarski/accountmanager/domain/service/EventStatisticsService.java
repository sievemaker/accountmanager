package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics;

public interface EventStatisticsService {
    EventStatistics updateStatistics(Account account, Event event);
}
