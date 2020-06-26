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
        1 * eventStatisticsRepository.findByTypeAndAccountAndHappenedAt(event.getType(), account, now) >> existingEventStatistics
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
        1 * eventStatisticsRepository.findByTypeAndAccountAndHappenedAt(event.getType(), account, now) >> null
        eventStatistics.getCount() == 1
        eventStatistics.getType() == event.getType()
        eventStatistics.getAccount() == event.getAccount()
        eventStatistics.getHappenedAt() == event.getHappenedAt().truncatedTo(ChronoUnit.DAYS)
    }
}
