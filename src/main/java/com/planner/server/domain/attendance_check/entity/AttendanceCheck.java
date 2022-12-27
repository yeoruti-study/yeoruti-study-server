package com.planner.server.domain.attendance_check.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.planner.server.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attendance_check")
@Getter
public class AttendanceCheck {

    @Id
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
}
