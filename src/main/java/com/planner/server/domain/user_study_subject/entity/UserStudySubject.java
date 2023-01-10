package com.planner.server.domain.user_study_subject.entity;

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

import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "user_study_subject")
@AllArgsConstructor
@NoArgsConstructor
public class UserStudySubject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "")
    private List<Record> records = new ArrayList<>();

    public void addRecord(Record record) {
        this.addRecord(record);
        record.setUserStudySubject(this);
    }
}
