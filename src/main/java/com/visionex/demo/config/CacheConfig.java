package com.visionex.demo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/
/**
 /**
 * Configuration class for caching in the application.
 * This class sets up a Caffeine-based CacheManager with specific caching policies.
 */
@Configuration
public class CacheConfig {

    /**
     * Configures a CacheManager bean using CaffeineCacheManager.
     * - Cache name: "weatherSummary"
     * - Caffeine-specific settings, such as expiration and async mode, are applied.
     * - Null values are disallowed in the cache.
     * @return a CacheManager instance configured with Caffeine settings
     */
    @Bean
    public CacheManager cacheManager() {
        // Create a CaffeineCacheManager for managing "weatherSummary" cache
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("weatherSummary");

        // Apply the cache builder with Caffeine settings
        cacheManager.setCaffeine(caffeineCacheBuilder());

        // Disallow caching null values to avoid unexpected behavior
        cacheManager.setAllowNullValues(false);

        // Enable asynchronous cache operations for improved performance
        cacheManager.setAsyncCacheMode(true);

        return cacheManager;
    }

    /**
     * Configures a Caffeine cache builder with specific settings.
     * - Sets a time-to-live (TTL) of 30 minutes for cached entries.
     * @return a Caffeine builder instance with the defined caching policies
     */
    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        // Define Caffeine settings: expire entries 30 minutes after write
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES);
    }
}

