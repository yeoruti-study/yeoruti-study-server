package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class StudyRoomCategory {
    @Id @GeneratedValue
    @Column(name = "study_room_category_id")
    private Long cid;

    private UUID id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "studyRoomCategory", cascade = CascadeType.ALL)
    private List<StudyRoom> studyRooms = new ArrayList<>();

    public void addStudyRoom(StudyRoom studyRoom){
        this.studyRooms.add(studyRoom);
        studyRoom.setStudyRoomCategory(this);
    }

    public StudyRoomCategory(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public StudyRoomCategory() {
    }
}
