package com.planner.server.domain.study_category;

import com.planner.server.domain.study_room.StudyRoom;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyCategory {

    @Id
    @NotNull
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "studyCategory")
    private List<StudyRoom> studyRooms = new ArrayList<>();

    public void addStudyRoom(StudyRoom studyRoom){
        this.studyRooms.add(studyRoom);
        studyRoom.setStudyCategory(this);
    }

}
