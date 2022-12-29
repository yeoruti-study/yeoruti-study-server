package com.planner.server.domain.study_goal.entity;

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

import org.hibernate.annotations.Type;

import com.planner.server.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "study_goal")
@Getter
public class StudyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    private String goalTitle;

    private String goalDetail;

    private Duration goalTime;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
