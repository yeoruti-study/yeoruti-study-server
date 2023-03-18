package com.planner.server.domain.record.entity;

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

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.*;
import org.hibernate.annotations.Type;

import com.planner.server.domain.user.entity.User;

@Entity
@Table(name = "record")
@Getter
@NoArgsConstructor
public class Record implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;
    
    private LocalDateTime startTime;

    @Setter
    private LocalDateTime endTime;

    @Setter
    private Duration totalStudyTime;

    @Setter
    private boolean studying;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_study_subject_id", referencedColumnName = "id")
    private UserStudySubject userStudySubject;

    @Builder
    public Record(Long cid, UUID id, LocalDateTime startTime, LocalDateTime endTime, Duration totalStudyTime, boolean studying, User user, UserStudySubject userStudySubject) {
        this.cid = cid;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalStudyTime = totalStudyTime;
        this.studying = studying;
        this.user = user;
        this.userStudySubject = userStudySubject;
    }
}
