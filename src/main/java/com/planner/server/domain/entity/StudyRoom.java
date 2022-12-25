package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "studyRoom")
    private List<RoomChat> roomChats = new ArrayList<>();

    public void setStudyRoomCategory(StudyRoomCategory studyRoomCategory) {
        this.studyRoomCategory = studyRoomCategory;
    }

    public StudyRoom() {
    }

    public StudyRoom(UUID id, String name, int maximumNumberOfPeople, LocalTime studyGoalTime, String roomPassword, LocalDateTime createdAt, LocalDateTime updatedAt, StudyRoomCategory studyRoomCategory, List<RoomChat> roomChats) {
        this.id = id;
        this.name = name;
        this.maximumNumberOfPeople = maximumNumberOfPeople;
        this.studyGoalTime = studyGoalTime;
        this.roomPassword = roomPassword;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studyRoomCategory = studyRoomCategory;
        this.roomChats = roomChats;
    }
}