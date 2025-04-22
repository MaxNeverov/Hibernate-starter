package com.mndev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstname;
    private String lastname;
    private LocalDate birthDate;


    //    @Convert(converter =  BirthdayConverter.class)
//    @Column(name = "birth_date")
//    private Birthday birthDate;

}
