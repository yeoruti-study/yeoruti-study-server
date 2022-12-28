package com.planner.server.domain.study_goal;

import com.planner.server.domain.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyGoal {

    @Id
    @NotNull
    @Type(type = "uuid-char")
    private UUID id;

    private String goalTitle;

    private String goalDetail;

    private Duration goalTime;

    private LocalDate startDate;

    private LocalDate endDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
