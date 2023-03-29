package com.planner.server.domain.user.entity;


import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_goal.entity.StudyGoal;

import com.planner.server.domain.user.dto.UserReqDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.*;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Type(type = "uuid-char")
    private UUID id;

    private String username;

    private String password;

    @Type(type = "uuid-char")
    private UUID salt;

    private String roles;

    private String provider;

    private String profileName;

    private String profileBirth;

    @Setter
    private String profileImagePath;

    private boolean friendAcceptance;

    private boolean alarmPermission;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public User(UUID id, String username, String password, UUID salt, String roles, String provider, String profileName, String profileBirth, String profileImagePath, boolean friendAcceptance, boolean alarmPermission, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
        this.provider = provider;
        this.profileName = profileName;
        this.profileBirth = profileBirth;
        this.profileImagePath = profileImagePath;
        this.friendAcceptance = friendAcceptance;
        this.alarmPermission = alarmPermission;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<StudyGoal> studyGoals = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AttendanceCheck> attendanceChecks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomUser> roomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<UserStudySubject> userStudySubjects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Friend> friends = new ArrayList<>();

    public void changeUserInfo(UserReqDto.ReqUpdateProfile reqDto) {
        this.profileName = reqDto.getProfileName();
        this.profileBirth = reqDto.getProfileBirth();
        this.friendAcceptance = reqDto.isFriendAcceptance();
        this.alarmPermission = reqDto.isAlarmPermission();
        this.profileImagePath = reqDto.getProfileImageUrl();
    }

}
