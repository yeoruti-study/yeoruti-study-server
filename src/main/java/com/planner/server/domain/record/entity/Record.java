package com.planner.server.domain.record.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "record")
@Getter
public class Record {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;

    private Duration totalStudyTime;

    private boolean studying;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_category_subject_id")
    private UserStudySubject userStudySubject;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
