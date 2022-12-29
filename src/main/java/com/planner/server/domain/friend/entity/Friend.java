package com.planner.server.domain.friend.entity;

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

import org.hibernate.annotations.Type;

import com.planner.server.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity(name = "friend")
@Getter
public class Friend {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;
    
    @Type(type = "uuid-char")
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

    private boolean allow;
    
    private LocalDateTime createdAt;
}
