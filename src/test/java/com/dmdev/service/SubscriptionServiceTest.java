package com.dmdev.service;

import com.dmdev.dao.SubscriptionDao;
import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.mapper.CreateSubscriptionMapper;
import com.dmdev.validator.CreateSubscriptionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {


    @Mock
    private CreateSubscriptionValidator createSubscriptionValidator;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private CreateSubscriptionMapper createSubscriptionMapper;
    @Mock
    private Clock clock;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void subscriptionExists() {
        Subscription existingSubscription = Subscription.builder()
                .userId(1)
                .name("Ivan")
                .provider(Provider.APPLE)
                .expirationDate(Instant.now().plus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS))
                .status(Status.ACTIVE)
                .build();

        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Ivan")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().plus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS))
                .build();

        Subscription actualResult = subscriptionService.upsert(dto);

        doReturn(List.of(existingSubscription)).when(subscriptionDao).findByUserId(dto.getUserId());
//        doReturn(existingSubscription).when(List.of(existingSubscription)).get();
        doReturn(existingSubscription).when(subscriptionDao).upsert(any(Subscription.class));

        org.assertj.core.api.Assertions.assertThat(actualResult).isEqualTo(existingSubscription);

    }

//    @Test
//    void subscriptionNotExists() {
//
//    }
//
//    @Test
//    void cancel() {
//    }
//
//    @Test
//    void expire() {
//    }
}