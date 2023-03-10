package com.planner.server.domain.user_study_subject.entity;

import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user_study_subject")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStudySubject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Type(type = "uuid-char")
    private UUID id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "userStudySubject")
    private List<Record> records;

    @Builder
    public UserStudySubject(Long cid, UUID id, String title, User user, List<Record> records) {
        this.cid = cid;
        this.id = id;
        this.title = title;
        this.user = user;
        this.records = records;
    }
}
