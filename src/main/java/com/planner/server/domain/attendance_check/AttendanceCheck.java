package com.planner.server.domain.attendance_check;

import com.planner.server.domain.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCheck {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    @Type(type = "uuid-char")
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

}
