package com.planner.server.domain.study_category;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.planner.server.domain.study_room.StudyRoomEntity;

import lombok.Getter;

@Entity
@Table(name = "study_category")
@Getter
public class StudyCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "studyCategory")
    List<StudyRoomEntity> studyRooms = new ArrayList<>();

    public void addStudyRoom(StudyRoomEntity studyRoom) {
        this.studyRooms.add(studyRoom);
        studyRoom.setStudyCategory(this);
    }
}
