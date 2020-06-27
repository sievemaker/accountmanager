package io.github.lrzeszotarski.accountmanager.scheduler

import io.github.lrzeszotarski.accountmanager.domain.service.EventCleanupService
import spock.lang.Specification

class ScheduledTasksTest extends Specification {

    def eventCleanupService = Mock(EventCleanupService)

    def testedInstance = new ScheduledTasks(eventCleanupService)

    def "test cleanupOutdatedEvents"() {
        when:
        testedInstance.cleanupOutdatedEvents()
        then:
        1 * eventCleanupService.cleanupEvents()
    }
}
