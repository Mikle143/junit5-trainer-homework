package com.dmdev.validator;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

//import static sun.jvm.hotspot.interpreter.Bytecodes.name;

class CreateSubscriptionValidatorTest {

    private final CreateSubscriptionValidator validator = CreateSubscriptionValidator.getInstance();

    @Test
    void shouldPassValidation() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Ivan")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().plus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS))
                .build();
        ValidationResult actualResult = validator.validate(dto);
        assertFalse(actualResult.hasErrors());
    }

    @Test
    void invalidProvider() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Ivan")
                .provider("Samsung")
                .expirationDate(Instant.now().plus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS))
                .build();
        ValidationResult actualResult = validator.validate(dto);
        org.assertj.core.api.Assertions.assertThat(actualResult.getErrors()).hasSize(1);
        org.assertj.core.api.Assertions.assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo(102);
    }
}