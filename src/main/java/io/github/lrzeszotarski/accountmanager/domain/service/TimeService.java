package io.github.lrzeszotarski.accountmanager.domain.service;

import java.time.OffsetDateTime;

public interface TimeService {
    OffsetDateTime get30DaysAgoDate();
}
