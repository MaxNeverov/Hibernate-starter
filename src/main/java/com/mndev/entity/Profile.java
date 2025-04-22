package com.mndev.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Builder
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String street;
    private String language;


    //Для установки профайла в пользователя
    public void setUser(UserEntity user) {
//        user.setProfile(this);
        this.user = user;
    }
}
