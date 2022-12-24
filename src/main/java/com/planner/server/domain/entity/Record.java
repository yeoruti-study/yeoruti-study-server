package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class Record {
    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long cid;

    private UUID id;

    private LocalDateTime recordStartTime;
    private LocalDateTime recordEndTime;
    private Duration totalStudyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public void setUser(User user) {
        this.user = user;
    }
}
