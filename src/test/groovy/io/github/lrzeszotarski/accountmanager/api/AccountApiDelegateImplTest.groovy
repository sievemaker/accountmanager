package io.github.lrzeszotarski.accountmanager.api

import io.github.lrzeszotarski.accountmanager.api.model.Account
import io.github.lrzeszotarski.accountmanager.api.model.Event
import io.github.lrzeszotarski.accountmanager.api.model.EventStatistics
import io.github.lrzeszotarski.accountmanager.domain.service.AccountService
import io.github.lrzeszotarski.accountmanager.domain.service.EventStatisticsService
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper
import io.github.lrzeszotarski.accountmanager.mapper.EventMapper
import io.github.lrzeszotarski.accountmanager.mapper.EventStatisticsMapper
import spock.lang.Specification

import java.time.OffsetDateTime

class AccountApiDelegateImplTest extends Specification {

    def accountMapper  = Mock(AccountMapper)

    def eventMapper = Mock(EventMapper)

    def eventStatisticsMapper = Mock(EventStatisticsMapper)

    def accountService = Mock(AccountService)

    def eventStatisticsService = Mock(EventStatisticsService)

    def testedInstance = new AccountApiDelegateImpl(accountMapper, eventMapper, eventStatisticsMapper, accountService, eventStatisticsService)

    def "CreateAccount"() {
        given:
        def account = new Account()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.createAccount(account)
        then:
        1 * accountMapper.toEntity(account) >> accountEntity
        1 * accountService.createAccount(accountEntity) >> accountEntity
        1 * accountMapper.toDto(accountEntity)
    }

    def "FindAccount"() {
        given:
        def accountId = UUID.randomUUID().toString()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.getAccounts(accountId)
        then:
        1 * accountService.findAccount(UUID.fromString(accountId)) >> accountEntity
        1 * accountMapper.toDto(accountEntity)
    }

    def "UpdateAccount"() {
        given:
        def account = new Account()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        def updatedEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.updateAccount(account)
        then:
        1 * accountMapper.toEntity(account) >> accountEntity
        1 * accountService.updateAccount(accountEntity) >> updatedEntity
        1 * accountMapper.toDto(updatedEntity)
    }

    def "CreateEvent"() {
        given:
        def event = new Event()
        def eventEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Event()
        def accountId = UUID.randomUUID().toString()
        when:
        testedInstance.createEvent(accountId, event)
        then:
        1 * eventMapper.toEntity(event) >> eventEntity
        1 * accountService.createEvent(UUID.fromString(accountId), eventEntity) >> eventEntity
        1 * eventMapper.toDto(eventEntity)
    }

    def "FindEvent"() {
        given:
        def accountId = UUID.randomUUID().toString()
        def eventId = UUID.randomUUID().toString()
        def eventEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Event()
        when:
        testedInstance.searchEvent(accountId, eventId)
        then:
        1 * accountService.findEvent(UUID.fromString(accountId), UUID.fromString(eventId)) >> eventEntity
        1 * eventMapper.toDto(eventEntity)
    }

    def "GetAccountStatistics"() {
        given:
        def accountId = UUID.randomUUID().toString()
        def now = OffsetDateTime.now()
        def statistics = List.of(
                new io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics(),
                new io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics())
        when:
        testedInstance.getAccountStatistics(accountId, now)
        then:
        1 * eventStatisticsService.getStatistics(UUID.fromString(accountId), now) >> statistics
        1 * eventStatisticsMapper.toDto(statistics.get(0))
        1 * eventStatisticsMapper.toDto(statistics.get(1))
    }
}
