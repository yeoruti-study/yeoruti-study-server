package com.planner.server.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Friend {

    @Id @GeneratedValue
    @Column(name = "friend_list_id")
    private Long cid;

    private UUID id;

    private boolean allowYn;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Long friend_id;

    public void setUser(User user) {
        this.user = user;
    }

    public Friend() {
    }

    public Friend(UUID id, boolean allowYn, LocalDateTime createdAt) {
        this.id = id;
        this.allowYn = allowYn;
        this.createdAt = createdAt;
    }
}
