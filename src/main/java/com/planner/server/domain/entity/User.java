package com.planner.server.domain.entity;

import com.planner.server.Role;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @NotNull
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "username")
    private String userName;

    private String password;

    @Column(name = "nickname")
    private String nickName;

    private UUID salt;

    private Role roles;

    private String profileName;

    private short profileAge;

    private Boolean alarmPermission;

    private Boolean friendAcceptance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyGoal> studyGoals = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomChat> roomChats = new ArrayList<>();

    public void addRecord(Record record) {
        this.records.add(record);
        record.setUser(this);
    }

    public void addAttendance(Attendance attendance){
        this.attendances.add(attendance);
        attendance.setUser(this);
    }
    public void addStudyGoal(StudyGoal studyGoal) {
        this.studyGoals.add(studyGoal);
        studyGoal.setUser(this);
    }

    public void addRoomChats(RoomChat roomChat){
        this.roomChats.add(roomChat);
        roomChat.setUser(this);
    }

    public void addFriend(User friend) {

    }

}
