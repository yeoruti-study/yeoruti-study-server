package com.planner.server.domain.user.entity;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.user.dto.UpdateProfileReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    @Type(type = "uuid-char")
    private UUID id;

    private String username;

    private String password;

    private UUID salt;

    private String roles;

    private String profileName;

    private int profileAge;

    private String profileImagePath;

    private boolean friendAcceptance;

    private boolean alarmPermission;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public User(UUID id, String username, String password, UUID salt, String roles, String profileName, int profileAge, String profileImagePath, boolean friendAcceptance, boolean alarmPermission, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
        this.profileName = profileName;
        this.profileAge = profileAge;
        this.profileImagePath = profileImagePath;
        this.friendAcceptance = friendAcceptance;
        this.alarmPermission = alarmPermission;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyGoal> studyGoals = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AttendanceCheck> attendanceChecks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomUser> roomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();

    public void addRoomUser(RoomUser roomUser) {
        this.roomUsers.add(roomUser);
        roomUser.setUser(this);
    }

    public void addRecord(Record record) {
        this.records.add(record);
        record.setUser(this);
    }

    public void addStudyGoal(StudyGoal studyGoal) {
        this.studyGoals.add(studyGoal);
        studyGoal.setUser(this);
    }

    public void addAttendanceCheck(AttendanceCheck attendanceCheck) {
        this.attendanceChecks.add(attendanceCheck);
        attendanceCheck.setUser(this);
    }

    public void addFriend(Friend friend) {
        this.friends.add(friend);
        friend.setUser(this);
    }

    public void fixProfile(UpdateProfileReqDto profileDto) {
        this.profileName = profileDto.getProfileName();
        this.profileAge = profileDto.getProfileAge();
        this.profileImagePath = profileDto.getProfileImagePath();
        this.friendAcceptance = profileDto.isFriendAcceptance();
        this.alarmPermission = profileDto.isAlarmPermission();
    }

    public void fixPassword(String password){
        this.password = password;
    }

    public void fixFriendAcceptance(){
        this.friendAcceptance = !this.friendAcceptance;
    }

    public void fixAlarmPermission(){
        this.alarmPermission = !this.alarmPermission;
    }

    public void update(){
        this.updatedAt = LocalDateTime.now();
    }
}
