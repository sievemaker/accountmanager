package io.github.lrzeszotarski.accountmanager.domain.service;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TimeServiceImpl implements TimeService {
    @Override
    public OffsetDateTime get30DaysAgoDate() {
        return OffsetDateTime.now().minus(30L, ChronoUnit.DAYS);
    }
}
