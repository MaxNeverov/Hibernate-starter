package com.mndev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "usersChats")
@EqualsAndHashCode(of = "name")
@Builder
//JPA
@Entity
public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /// /////////////////////////////////////////////////////////////////////////////////
    /// ManyToMany
//    @Builder.Default
//    @ManyToMany(mappedBy = "chats")
//    private List<UserEntity> users = new ArrayList<>();
    /// /////////////////////////////////////////////////////////////////////////////////

    //ManyToMany 2 вариант
    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private List<UsersChats> usersChats = new ArrayList<>();
    // /////////////////////////////////////////////////////////////////////////////////

}
