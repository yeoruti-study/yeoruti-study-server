package com.planner.server.domain.study_goal.entity;

import java.io.Serializable;
import java.time.Duration;
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
@Table(name = "study_goal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyGoal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    private String goalTitle;

    private String goalDetail;

    private Duration goalTime;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Type(type = "uuid-char")
    private UUID userStudySubjectId;

    @Builder
    public StudyGoal(UUID id, String goalTitle, String goalDetail, Duration goalTime, LocalDateTime startDate, LocalDateTime endDate, User user, UUID userStudySubjectId) {
        this.id = id;
        this.goalTitle = goalTitle;
        this.goalDetail = goalDetail;
        this.goalTime = goalTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.userStudySubjectId = userStudySubjectId;
    }

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
