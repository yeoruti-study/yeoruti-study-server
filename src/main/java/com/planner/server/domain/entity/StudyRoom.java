package com.planner.server.domain.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private int maximumNumberOfPeople;

    private LocalTime studyGoalTime;

    private String roomPassword;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_category_id")
    private StudyCategory studyCategory;

    @OneToMany(mappedBy = "studyRoom")
    private List<RoomChat> roomChats = new ArrayList<>();

    public void setStudyCategory(StudyCategory studyCategory) {
        this.studyCategory = studyCategory;
    }

}