package com.mndev.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users", "chat"})
@Builder
//JPA
@Table(name = "users_chats")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsersChats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne
    private Chats chat;

    @Column(name = "add_by")
    private String addBy;

    public void setChat(Chats chat) {
        this.chat = chat;
        this.chat.getUsersChats().add(this);
    }

    public void setUser(UserEntity user) {
        this.user = user;
        this.user.getUsersChats().add(this);
    }
}
