package com.planner.server.domain.attendance_check.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.Type;

import com.planner.server.domain.user.entity.User;

@Entity
@Table(name = "attendance_check")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceCheck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    @Builder
    public AttendanceCheck(UUID id, User user, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private LocalDateTime createdAt;
}
