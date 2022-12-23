package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class AttendanceCheck {

    @Id @GeneratedValue
    @Column(name = "attendence_check_id")
    private Long cid;

    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
}
