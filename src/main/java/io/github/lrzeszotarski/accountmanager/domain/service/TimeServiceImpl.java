package io.github.lrzeszotarski.accountmanager.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TimeServiceImpl implements TimeService {

    @Value("${timeservice.eventValidityPeriodInDays}")
    private Integer eventValidityPeriodInDays;

    @Override
    public OffsetDateTime getMinimumDateForValidEvents() {
        return OffsetDateTime.now().minus(eventValidityPeriodInDays, ChronoUnit.DAYS);
    }
}
