package org.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Email {

    @Column(nullable = false)
    private String value;

    private Email(String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!value.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address: " + value);
        }

        this.value = value.toLowerCase().trim();
    }

    public static Email of(String value) {
        return new Email(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
