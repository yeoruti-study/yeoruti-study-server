package com.planner.server.domain.room_user;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.planner.server.domain.study_room.StudyRoomEntity;
import com.planner.server.domain.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "room_user")
@Getter
public class RoomUserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    @Column(name = "room_user_id")
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    private StudyRoomEntity studyRoom;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
