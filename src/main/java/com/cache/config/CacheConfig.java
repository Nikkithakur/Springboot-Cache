package com.cache.config;

import java.util.List;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

	@Bean
	public ConcurrentMapCacheManager cacheMngr() {
		ConcurrentMapCacheManager cacheMngr = new ConcurrentMapCacheManager();
		cacheMngr.setCacheNames(List.of("c1", "c2", "c3"));
		return cacheMngr;
	}
}
