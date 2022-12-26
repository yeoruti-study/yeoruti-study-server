package com.planner.server.domain.study_room;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.planner.server.domain.room_chat.RoomChatEntity;
import com.planner.server.domain.room_user.RoomUserEntity;
import com.planner.server.domain.study_category.StudyCategoryEntity;
import com.planner.server.domain.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "study_room")
@Getter
public class StudyRoomEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer maximumNumberOfPeople;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_category_id")
    private StudyCategoryEntity studyCategory;

    private Duration studyGoalTime;

    private String roomPassword;

    private LocalDateTime createdAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "studyRoom")
    List<RoomUserEntity> roomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "studyRoom")
    List<RoomChatEntity> roomChats = new ArrayList<>();

    public void addRoomUser(RoomUserEntity roomUser) {
        this.roomUsers.add(roomUser);
        roomUser.setStudyRoom(this);
    }

    public void addRoomChat(RoomChatEntity roomChat) {
        this.roomChats.add(roomChat);
        roomChat.setStudyRoom(this);
    }
}
