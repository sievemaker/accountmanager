package io.github.lrzeszotarski.accountmanager.mapper

import io.github.lrzeszotarski.accountmanager.domain.entity.Event
import spock.lang.Specification

import java.time.OffsetDateTime

class EventMapperTest extends Specification {
    def testedInstance = new EventMapperImpl()

    def "test toDto"() {
        given:
        def randomUUID = UUID.randomUUID()
        def now = OffsetDateTime.now()
        Event event = new Event(type: "Sample Type", eventId: randomUUID, happenedAt: now, id: 10)
        when:
        def eventDto = testedInstance.toDto(event)
        then:
        eventDto.getEventId() == randomUUID
        eventDto.getType() == "Sample Type"
        eventDto.getHappenedAt() == now
    }

    def "test toEntity"() {
        given:
        def randomUUID = UUID.randomUUID()
        def now = OffsetDateTime.now()
        io.github.lrzeszotarski.accountmanager.api.model.Event eventDto =
                new io.github.lrzeszotarski.accountmanager.api.model.Event(type: "Sample Type", eventId: randomUUID, happenedAt: now)
        when:
        def event = testedInstance.toEntity(eventDto)
        then:
        event.getId() == null
        event.getEventId() == randomUUID
        event.getType() == "Sample Type"
        event.getHappenedAt() == now
    }
}
