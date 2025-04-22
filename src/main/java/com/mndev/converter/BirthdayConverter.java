package com.mndev.converter;

import com.mndev.entity.Birthday;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Optional;

@Converter(autoApply = true)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {
    @Override
    public Date convertToDatabaseColumn(Birthday birthday) {
        return Optional.ofNullable(birthday)
                .map(Birthday::getBirthDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbDate) {
        return Optional.ofNullable(dbDate)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }
}
