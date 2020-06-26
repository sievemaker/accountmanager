package io.github.lrzeszotarski.accountmanager.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class EventStatistics {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private OffsetDateTime happenedAt;
    private String type;
    private Long count;

    @ManyToOne
    private Account account;
}
