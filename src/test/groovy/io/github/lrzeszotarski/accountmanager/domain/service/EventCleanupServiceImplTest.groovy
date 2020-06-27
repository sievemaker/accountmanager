package io.github.lrzeszotarski.accountmanager.domain.service

import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository
import spock.lang.Specification

import java.time.OffsetDateTime

class EventCleanupServiceImplTest extends Specification {

    def eventRepository = Mock(EventRepository)

    def timeService = Mock(TimeService)

    def testedInstance = new EventCleanupServiceImpl(eventRepository, timeService)

    def "test cleanupEvents"() {
        given:
        def someDate = OffsetDateTime.now()
        when:
        testedInstance.cleanupEvents()
        then:
        1 * timeService.getMinimumDateForValidEvents() >> someDate
        1 * eventRepository.deleteByHappenedAtBefore(someDate)
    }
}
