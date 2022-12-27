package com.planner.server.domain.study_category.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.planner.server.domain.study_room.entity.StudyRoom;

import lombok.Getter;

@Entity
@Table(name = "study_category")
@Getter
public class StudyCategory {

    @Id
    private UUID id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "studyCategory")
    List<StudyRoom> studyRooms = new ArrayList<>();

    public void addStudyRoom(StudyRoom studyRoom) {
        this.studyRooms.add(studyRoom);
        studyRoom.setStudyCategory(this);
    }
}
