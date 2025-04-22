package com.mndev.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
public final class Birthday {
     LocalDate birthDate;

    public long getAge(){
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
