package com.planner.server.domain.entity;

import com.planner.server.Role;
import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    @NotNull
    private Long cid;
    
    private UUID id;

    private String userName;
    private String passWord;
    private String nickName;

    private String profileName;
    private short profileAge;

    private Role roles;
    private boolean friendRecommendPermission;
    private boolean alarmPermission;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyGoal> studyGoals = new ArrayList<>();

    public void addRecord(Record record) {
        this.records.add(record);
        record.setUser(this);
    }

    public void addStudyGoal(StudyGoal studyGoal) {
        this.studyGoals.add(studyGoal);
        studyGoal.setUser(this);
    }
}
