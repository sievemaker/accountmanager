package io.github.lrzeszotarski.accountmanager.mapper;

import io.github.lrzeszotarski.accountmanager.api.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toDto(io.github.lrzeszotarski.accountmanager.domain.entity.Event event);

    io.github.lrzeszotarski.accountmanager.domain.entity.Event toEntity(Event event);
}
