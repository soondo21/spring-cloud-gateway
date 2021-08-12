package com.nh.test.scg.config;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Configuration
public class Resilience4jConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
	// @Bean
	// public Customizer<ReactiveResilience4JCircuitBreakerFactory> nhCircuitBreadker() {
    //     logger.info("[Resilience4jConfig : nhCircuitBreadker] Resilience4j Circuit Breaker 실행");
    //     CircuitBreakerConfig config = CircuitBreakerConfig.custom()
    //         .slidingWindowType(SlidingWindowType.COUNT_BASED)
    //         .slidingWindowSize(10) // 통계건수
    //         .minimumNumberOfCalls(2) // 최소요청횟수
    //         .failureRateThreshold(60) // 실패율
    //         .waitDurationInOpenState(Duration.ofMillis(10000)) // Circuit Breaker유지시간
    //         .build();
        
    //     // return factory -> factory.configure(builder -> builder., ids);
	// 	return factory -> factory.configure(builder -> builder.circuitBreakerConfig(config)
	// 		.build(), "nhCircuitBreadker");
	// }

    @Bean
    public CircuitBreaker circuitBreakerConfig() {
        // Create a custom configuration for a CircuitBreaker
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
        .failureRateThreshold(70)
        .waitDurationInOpenState(Duration.ofMillis(8000))
        .permittedNumberOfCallsInHalfOpenState(2)
        .slidingWindowSize(10)
        .recordExceptions(IOException.class, TimeoutException.class)
        .build();

        // Create a CircuitBreakerRegistry with a custom global configuration
        CircuitBreakerRegistry   circuitBreakerRegistry =
        CircuitBreakerRegistry.of(circuitBreakerConfig);

        return circuitBreakerRegistry.circuitBreaker("nhCircuitBreaker");
    }

    @Bean
    public TimeLimiter timeLimiterConfig() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
        .cancelRunningFuture(true)
        .timeoutDuration(Duration.ofMillis(7000))
        .build();

        // Create a TimeLimiterRegistry with a custom global configuration
        TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of(config);

        // Get or create a TimeLimiter from the registry - 
        // TimeLimiter will be backed by the default config
        return timeLimiterRegistry.timeLimiter("nhCircuitBreaker");
    }


    // @Bean
    // public CircuitBreakerConfigCustomizer testCustomizer() {
    //     return CircuitBreakerConfigCustomizer
    //         .of("backendA", builder -> builder.slidingWindowSize(100));
    // }
}