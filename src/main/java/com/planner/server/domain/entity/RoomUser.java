package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class RoomUser {
    @Id @GeneratedValue
    @Column(name = "room_user_id")
    private Long cid;

    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    public void setUser(User user){
        this.user = user;
    }
    public void setStudyRoom(StudyRoom studyRoom){
        this.studyRoom = studyRoom;
    }
}
