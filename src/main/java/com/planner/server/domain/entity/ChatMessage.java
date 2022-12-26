package com.planner.server.domain.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    @NotNull
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "chat_message_id")
    private ChatRoom chatRoom;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
