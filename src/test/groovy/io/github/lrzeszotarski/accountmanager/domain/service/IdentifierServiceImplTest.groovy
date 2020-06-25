package io.github.lrzeszotarski.accountmanager.domain.service

import spock.lang.Specification

class IdentifierServiceImplTest extends Specification {

    def testedInstance = new IdentifierServiceImpl()

    def "test generateIdentifier"() {
        given:
        when:
        def uuid = testedInstance.generateIdentifier()
        then:
        uuid != null
    }
}
