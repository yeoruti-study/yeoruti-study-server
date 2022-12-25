package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class RoomChat {

    @Id @GeneratedValue
    @Column(name = "room_chat_id")
    private Long cid;

    private UUID id;

    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    public RoomChat() {
    }

    public RoomChat(UUID id, String content, LocalDateTime createdAt, User user, StudyRoom studyRoom) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.studyRoom = studyRoom;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
