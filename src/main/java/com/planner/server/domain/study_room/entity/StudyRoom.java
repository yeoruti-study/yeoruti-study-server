package com.planner.server.domain.study_room.entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_category.entity.StudyCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table(name = "study_room")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StudyRoom implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    @Setter
    private String name;

    @Setter
    private int maximumNumberOfPeople;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_category_id", referencedColumnName="id")
    private StudyCategory studyCategory;

    @Setter
    private Duration studyGoalTime;

    @Setter
    private String roomPassword;
    
    private LocalDateTime createdAt;

    @Setter
    @Type(type = "uuid-char")
    private UUID masterUserId;

    @Setter
    private LocalDateTime updatedAt;

    @Setter
    private boolean hasRoomPassword;

    @OneToMany(mappedBy = "studyRoom")
    private List<RoomUser> roomUsers = new ArrayList<>();

    public void addRoomUser(RoomUser roomUser) {
        this.roomUsers.add(roomUser);
        roomUser.setStudyRoom(this);
    }
}
