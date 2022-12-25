package com.planner.server.domain.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @NotNull
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private LocalDateTime recordStartTime;

    private LocalDateTime recordEndTime;

    private Duration totalStudyTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
