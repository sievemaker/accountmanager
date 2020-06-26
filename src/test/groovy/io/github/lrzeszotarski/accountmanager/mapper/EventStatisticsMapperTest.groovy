package io.github.lrzeszotarski.accountmanager.mapper

import io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics
import spock.lang.Specification

import java.time.OffsetDateTime

class EventStatisticsMapperTest extends Specification {
    def testedInstance = new EventStatisticsMapperImpl()

    def "test toDto"() {
        given:
        def now = OffsetDateTime.now()
        EventStatistics event = new EventStatistics(type: "Sample Type", count: 10, day: now, id: 10)
        when:
        def eventDto = testedInstance.toDto(event)
        then:
        eventDto.getType() == "Sample Type"
        eventDto.getDay() == now
        eventDto.getCount() == 10
    }
}
