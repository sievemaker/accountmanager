package io.github.lrzeszotarski.accountmanager.domain.service

import io.github.lrzeszotarski.accountmanager.domain.entity.Account
import io.github.lrzeszotarski.accountmanager.domain.entity.Event
import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository
import io.github.lrzeszotarski.accountmanager.domain.repository.EventStatisticsRepository
import spock.lang.Specification

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

class EventStatisticsServiceImplTest extends Specification {

    def eventStatisticsRepository = Mock(EventStatisticsRepository)

    def accountRepository = Mock(AccountRepository)

    def testedInstance = new EventStatisticsServiceImpl(eventStatisticsRepository, accountRepository)

    def "test updateStatistics when statistics record exists"() {
        given:
        def account = new Account(eventStatisticsList: new ArrayList<EventStatistics>())
        def now = OffsetDateTime.now()
        def event = new Event(type: "Sample Type", account: account, happenedAt: now)
        def existingEventStatistics = new EventStatistics(count: 21)
        when:
        def eventStatistics = testedInstance.updateStatistics(account, event)
        then:
        1 * eventStatisticsRepository.findByTypeAndAccountAndDay(event.getType(), account, now.truncatedTo(ChronoUnit.DAYS)) >> existingEventStatistics
        eventStatistics.getCount() == 22
    }

    def "test updateStatistics when statistics record does not exist"() {
        given:
        def account = new Account(eventStatisticsList: new ArrayList<EventStatistics>())
        def now = OffsetDateTime.now()
        def event = new Event(type: "Sample Type", account: account, happenedAt: now)
        when:
        def eventStatistics = testedInstance.updateStatistics(account, event)
        then:
        1 * eventStatisticsRepository.findByTypeAndAccountAndDay(event.getType(), account, now.truncatedTo(ChronoUnit.DAYS)) >> null
        eventStatistics.getCount() == 1
        eventStatistics.getType() == event.getType()
        eventStatistics.getAccount() == event.getAccount()
        eventStatistics.getDay() == event.getHappenedAt().truncatedTo(ChronoUnit.DAYS)
    }

    def "test getStatistics"() {
        given:
        def accountId = UUID.randomUUID()
        def now = OffsetDateTime.now()
        def account = new Account(eventStatisticsList: new ArrayList<EventStatistics>())
        when:
        testedInstance.getStatistics(accountId, now)
        then:
        1 * accountRepository.findByAccountId(accountId) >> account
        1 * eventStatisticsRepository.findAllByAccountAndDay(account, now)
    }
}
