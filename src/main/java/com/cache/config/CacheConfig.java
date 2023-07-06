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

	@Bean
	public Caffeine<Object, Object> caffeine() {
		return Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10);
	}

	@Bean("caffeineManager")
	@Primary
	public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		caffeineCacheManager.setCacheNames(List.of("c1", "c2", "c3"));
		caffeineCacheManager.setAllowNullValues(true);
		return caffeineCacheManager;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return objMapper;
	}
}
