package com.mndev.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.mndev.util.StringUtils.SPACE;

//граф для решения проблемы N + 1
@NamedEntityGraph(
        name = "WithCompanyAndChat",
        attributeNodes = {
                //что надо будет подгружать
          @NamedAttributeNode("company"),
          @NamedAttributeNode(value = "usersChats", subgraph = "chats")
},
        //вложенная сущность в usersChats
        subgraphs = {
                @NamedSubgraph(name = "chats", attributeNodes = @NamedAttributeNode("chat"))
})

//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile", "userChats"})
@EqualsAndHashCode(of = "username")
@Builder
//JPA
@Table(name = "users", schema = "public")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Users")
public class UserEntity implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    //не работает в hibernate 6.0
//    @Type(type = "jsonb")
//    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;

    //    optional = false - делает поле not null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Company company;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Profile profile;

    /// /////////////////////////////////////////////////////////////////////////////////
//    //ManyToMany
//    @Builder.Default
//    @ManyToMany
//    @JoinTable(name = "users_chats",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "chat_id"))
//    private List<Chats> chats = new ArrayList<>();
//
//    public void addChat(Chats chat) {
//        chats.add(chat);
//        chat.getUsers().add(this);
//      }
    /// /////////////////////////////////////////////////////////////////////////////////
        //ManyToMany 2 вариант
        @Builder.Default
        @OneToMany(mappedBy = "user")
        @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
        private List<UsersChats> usersChats = new ArrayList<>();
        // /////////////////////////////////////////////////////////////////////////////////


    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();

    public String fullName() {
        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
    }
}

