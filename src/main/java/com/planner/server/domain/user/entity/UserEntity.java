package com.planner.server.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.planner.server.domain.attendance_check.AttendanceCheckEntity;
import com.planner.server.domain.friend.FriendEntity;
import com.planner.server.domain.record.RecordEntity;
import com.planner.server.domain.room_chat.RoomChatEntity;
import com.planner.server.domain.room_user.RoomUserEntity;
import com.planner.server.domain.study_goal.StudyGoalEntity;
import com.planner.server.domain.study_room.StudyRoomEntity;

import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private UUID salt;

    private String roles;

    private String profileName;

    private Integer profileAge;

    private boolean friendAcceptance;

    private boolean alarmPermission;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    List<StudyRoomEntity> studyRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<RecordEntity> records = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<StudyGoalEntity> studyGoals = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<AttendanceCheckEntity> attendanceChecks = new ArrayList<>();

    // friend의 friend
    @OneToMany(mappedBy = "user")
    List<FriendEntity> friends = new ArrayList<>();

    public void addStudyRoom(StudyRoomEntity studyRoom) {
        this.studyRooms.add(studyRoom);
        studyRoom.setUser(this);
    }

    public void addRecord(RecordEntity record) {
        this.records.add(record);
        record.setUser(this);
    }

    public void addStudyGoal(StudyGoalEntity studyGoal) {
        this.studyGoals.add(studyGoal);
        studyGoal.setUser(this);
    }

    public void addAttendanceCheck(AttendanceCheckEntity attendanceCheck) {
        this.attendanceChecks.add(attendanceCheck);
        attendanceCheck.setUser(this);
    }

    public void addFriend(FriendEntity friend) {
        this.friends.add(friend);
        friend.setUser(this);
    }
}
