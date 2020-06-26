package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.api.model.Event;
import io.github.lrzeszotarski.accountmanager.api.model.EventStatistics;
import io.github.lrzeszotarski.accountmanager.domain.service.AccountService;
import io.github.lrzeszotarski.accountmanager.domain.service.EventStatisticsService;
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper;
import io.github.lrzeszotarski.accountmanager.mapper.EventMapper;
import io.github.lrzeszotarski.accountmanager.mapper.EventStatisticsMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private final AccountMapper accountMapper;

    private final EventMapper eventMapper;

    private final EventStatisticsMapper eventStatisticsMapper;

    private final AccountService accountService;

    private final EventStatisticsService eventStatisticsService;

    public AccountApiDelegateImpl(AccountMapper accountMapper, EventMapper eventMapper, EventStatisticsMapper eventStatisticsMapper, AccountService accountService, EventStatisticsService eventStatisticsService) {
        this.accountMapper = accountMapper;
        this.eventMapper = eventMapper;
        this.eventStatisticsMapper = eventStatisticsMapper;
        this.accountService = accountService;
        this.eventStatisticsService = eventStatisticsService;
    }

    @Override
    public ResponseEntity<Account> createAccount(Account body) {
        final io.github.lrzeszotarski.accountmanager.domain.entity.Account entity = accountMapper.toEntity(body);
        return ResponseEntity.ok(accountMapper.toDto(accountService.createAccount(entity)));
    }

    @Override
    public ResponseEntity<Account> getAccounts(String accountId) {
        return ResponseEntity.ok(accountMapper.toDto(accountService.findAccount(UUID.fromString(accountId))));
    }

    @Override
    public ResponseEntity<Account> updateAccount(Account body) {
        final io.github.lrzeszotarski.accountmanager.domain.entity.Account entity = accountMapper.toEntity(body);
        return ResponseEntity.ok(accountMapper.toDto(accountService.updateAccount(entity)));
    }

    @Override
    public ResponseEntity<Event> createEvent(String accountId, Event body) {
        final io.github.lrzeszotarski.accountmanager.domain.entity.Event entity = eventMapper.toEntity(body);
        return ResponseEntity.ok(eventMapper.toDto(accountService.createEvent(UUID.fromString(accountId), entity)));
    }

    @Override
    public ResponseEntity<Event> searchEvent(String accountId, String eventId) {
        return ResponseEntity.ok(eventMapper.toDto(accountService.findEvent(UUID.fromString(accountId), UUID.fromString(eventId))));
    }

    @Override
    public ResponseEntity<List<EventStatistics>> getAccountStatistics(String accountId, OffsetDateTime day) {
        final List<io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics> statistics = eventStatisticsService.getStatistics(UUID.fromString(accountId), day);
        return ResponseEntity.ok(statistics.stream().map(eventStatisticsMapper::toDto).collect(Collectors.toList()));
    }
}
