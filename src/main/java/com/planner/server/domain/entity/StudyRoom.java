package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
public class StudyRoom {

    @Id @GeneratedValue
    @Column(name = "study_room_id")
    private Long cid;

    private UUID id;

    private String name;
    private int maximumNumberOfPeople;
    private LocalTime studyGoalTime;
    private String roomPassword;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_category_id")
    private StudyRoomCategory studyRoomCategory;

    public void setStudyRoomCategory(StudyRoomCategory studyRoomCategory) {
        this.studyRoomCategory = studyRoomCategory;
    }
}