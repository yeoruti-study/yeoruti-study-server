package com.planner.server.domain.user;

import com.planner.server.Role;
import com.planner.server.domain.attendance_check.AttendanceCheck;
import com.planner.server.domain.friend.Friend;
import com.planner.server.domain.record.Record;
import com.planner.server.domain.room_chat.RoomChat;
import com.planner.server.domain.study_goal.StudyGoal;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @NotNull
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "username")
    private String userName;

    private String password;

    private UUID salt;

    private String roles;

    private String profileName;

    private short profileAge;

    private boolean alarmPermission;

    private boolean friendAcceptance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AttendanceCheck> attendanceChecks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyGoal> studyGoals = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoomChat> roomChats = new ArrayList<>();

    public void addFriend(Friend friend){
        this.friends.add(friend);
        friend.setUser(this);
    }
    public void addAttendanceCheck(AttendanceCheck attendance){
        this.attendanceChecks.add(attendance);
        attendance.setUser(this);
    }

    public void addRecord(Record record) {
        this.records.add(record);
        record.setUser(this);
    }
    public void addStudyGoal(StudyGoal studyGoal) {
        this.studyGoals.add(studyGoal);
        studyGoal.setUser(this);
    }

    public void addRoomChats(RoomChat roomChat){
        this.roomChats.add(roomChat);
        roomChat.setUser(this);
    }

}
