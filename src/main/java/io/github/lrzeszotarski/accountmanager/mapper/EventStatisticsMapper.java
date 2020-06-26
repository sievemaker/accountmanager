package io.github.lrzeszotarski.accountmanager.mapper;

import io.github.lrzeszotarski.accountmanager.api.model.EventStatistics;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventStatisticsMapper {
    EventStatistics toDto(io.github.lrzeszotarski.accountmanager.domain.entity.EventStatistics eventStatistics);
}
