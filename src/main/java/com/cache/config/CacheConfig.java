package com.cache.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

	@Bean
	public CacheManager cacheMngr() {
		ConcurrentMapCacheManager cacheMngr = new ConcurrentMapCacheManager();
		cacheMngr.setCacheNames(List.of("c1", "c2", "c3"));
		return cacheMngr;
	}

	@Bean("caffeineManager")
	@Primary
	public CacheManager cacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.registerCustomCache("Countries",
				Caffeine.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).recordStats().build());
		caffeineCacheManager.registerCustomCache("States",
				Caffeine.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).recordStats().build());
		caffeineCacheManager.registerCustomCache("Technologies",
				Caffeine.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).recordStats().build());
		return caffeineCacheManager;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return objMapper;
	}
}
