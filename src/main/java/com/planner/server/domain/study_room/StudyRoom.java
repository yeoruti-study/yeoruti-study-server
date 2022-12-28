package com.planner.server.domain.study_room;

import com.planner.server.domain.study_category.StudyCategory;
import com.planner.server.domain.room_chat.RoomChat;
import com.planner.server.domain.room_user.RoomUser;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoom {

    @Id
    @NotNull
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    private int maximumNumberOfPeople;

    private LocalTime studyGoalTime;

    private String roomPassword;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_category_id")
    private StudyCategory studyCategory;

    @OneToMany(mappedBy = "studyRoom")
    private List<RoomUser> roomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "studyRoom")
    private List<RoomChat> roomChats = new ArrayList<>();

    public void addRoomUser(RoomUser roomUser){
        this.roomUsers.add(roomUser);
        roomUser.setStudyRoom(this);
    }

    public void addRoomChat(RoomChat roomChat){
        this.roomChats.add(roomChat);
        roomChat.setStudyRoom(this);
    }

}