package com.planner.server.domain.friend.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.Type;

import com.planner.server.domain.user.entity.User;

@Entity
@Table(name = "friend")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private User friend;

    private boolean allow;
    
    private LocalDateTime createdAt;

    @Builder
    public Friend(UUID id, User user, User friend, boolean allow, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.allow = allow;
        this.createdAt = createdAt;
    }

    public void fixAllowance(){
        boolean allowance = this.allow;
        this.allow = allowance;
    }
}
