package org.atlas.bank.atlas_bank.domain.model.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailDomainTest {

    @Test
    @DisplayName("Should create a valid EmailDomain")
    void shouldCreateValidEmailDomain() {
        String validEmail = "test@example.com";
        Email emailDomain = Email.of(validEmail);

        assertEquals(validEmail, emailDomain.getValue());
    }

    @Test
    @DisplayName("Should normalize email to lowercase")
    void shouldNormalizeEmailToLowercase() {
        String mixedCaseEmail = "Test@Example.com";
        Email emailDomain = Email.of(mixedCaseEmail);

        assertEquals("test@example.com", emailDomain.getValue());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for null email")
    void shouldThrowExceptionForNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> Email.of(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid email format")
    void shouldThrowExceptionForInvalidEmailFormat() {
        String invalidEmail = "invalid-email";
        assertThrows(IllegalArgumentException.class, () -> Email.of(invalidEmail));
    }
}