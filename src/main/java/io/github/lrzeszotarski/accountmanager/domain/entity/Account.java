package io.github.lrzeszotarski.accountmanager.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private UUID accountId;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Event> eventList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<EventStatistics> eventStatisticsList;
}
