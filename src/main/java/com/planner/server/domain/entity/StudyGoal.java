package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class StudyGoal {

    @Id @GeneratedValue
    @Column(name = "study_goal_id")
    private Long cid;

    private UUID id;

    private String goalTitle;
    private String goalDetail;
    private String goalTime;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setUser(User user) {
        this.user = user;
    }

    public StudyGoal() {
    }

    public StudyGoal(UUID id, String goalTitle, String goalDetail, String goalTime, LocalDate startDate, LocalDate endDate, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.goalTitle = goalTitle;
        this.goalDetail = goalDetail;
        this.goalTime = goalTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
