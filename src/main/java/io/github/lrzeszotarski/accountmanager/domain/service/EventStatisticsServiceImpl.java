package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.domain.repository.EventStatisticsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventStatisticsServiceImpl implements EventStatisticsService {

    private final EventStatisticsRepository eventStatisticsRepository;

    private final AccountRepository accountRepository;

    public EventStatisticsServiceImpl(EventStatisticsRepository eventStatisticsRepository, AccountRepository accountRepository) {
        this.eventStatisticsRepository = eventStatisticsRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public EventStatistics updateStatistics(Account account, Event event) {
        final EventStatistics eventStatistics = eventStatisticsRepository.findByTypeAndAccountAndDay(event.getType(), account, truncateToDate(event.getHappenedAt()));
        if (eventStatistics != null) {
            return updateEventStatistics(eventStatistics);
        }
        return createEventStatistics(account, event);
    }

    @Override
    public List<EventStatistics> getStatistics(UUID accountId, OffsetDateTime day) {
        final Account account = accountRepository.findByAccountId(accountId);
        return eventStatisticsRepository.findAllByAccountAndDay(account, day);
    }

    private EventStatistics createEventStatistics(Account account, Event event) {
        final EventStatistics eventStatistics = new EventStatistics();
        eventStatistics.setCount(1L);
        eventStatistics.setAccount(account);
        eventStatistics.setDay(truncateToDate(event.getHappenedAt()));
        eventStatistics.setType(event.getType());
        account.getEventStatisticsList().add(eventStatistics);
        return eventStatistics;
    }

    private EventStatistics updateEventStatistics(EventStatistics eventStatistics) {
        eventStatistics.setCount(eventStatistics.getCount() + 1);
        return eventStatistics;
    }

    private OffsetDateTime truncateToDate(final OffsetDateTime date) {
        return date.truncatedTo(ChronoUnit.DAYS);
    }
}
