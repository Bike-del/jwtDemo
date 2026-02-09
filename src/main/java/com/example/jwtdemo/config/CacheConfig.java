package com.example.jwtdemo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("products");

        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .maximumSize(10_000)  //algo use?
                        .recordStats()
        );
        return cacheManager;

    } //for large scale data set
    // intenal working, other type
    //
}
