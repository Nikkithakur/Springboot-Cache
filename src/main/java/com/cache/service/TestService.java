package com.cache.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.stats.CacheStats;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {

	@Autowired
	private CacheManager cacheManager;

	public void fetchAllContent() {
		log.info("{}", cacheManager instanceof CaffeineCacheManager);
		for (String cacheName : cacheManager.getCacheNames()) {
			CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);
			com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
			for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
				System.err.println("Key = " + entry.getKey());
				System.err.println("Value = " + entry.getValue());
			}
			
			CacheStats cacheStats = nativeCache.stats();
			System.err.println(String.format("evictionCount:%s , hitCount:%s , loadCount:%s",cacheStats.evictionCount(),
					cacheStats.hitCount(),
					cacheStats.loadCount()
					));
		}
	}

	@Cacheable(cacheNames="States")
	public HashMap<Object, Object> fetchDataFromDB() {
		log.info("fetchDataFromDB() invoked --------------------");
		return new HashMap<>() {
			{
				put("KA", "KARNATAKA");
				put("TG", "TELANGANA");
				put("TN", "TAMILNADU");
				put("JH", "JHARKHAND");

			}
		};
	}

	@Cacheable("Countries")
	public HashMap<Object, Object> fetchDataFromDownStream() {
		log.info("fetchDataFromDownStream() invoked --------------------");
		return new HashMap<>() {
			{
				put("IN", "INDIA");
				put("SL", "SRI LANKA");
				put("TN", "TAMILNADU");

			}
		};
	}

	@Cacheable("Technologies")
	public HashMap<Object, Object> fetchDataFrom3rdParty() {
		log.info("fetchDataFrom3rdParty() invoked --------------------");
		return new HashMap<>() {
			{
				put("SB", "SPRING BOOT");
				put("PY", "PYTHON");
			}
		};
	}

	@CachePut("States")
	public HashMap<Object, Object> fetchDataFromDBAlways() {
		log.info("fetchDataFromDBAlways() invoked --------------------");
		return new HashMap<>() {
			{
				put("KA", "KARNATAKA");
				put("TG", "TELANGANA");
				put("TN", "TAMILNADU");

			}
		};
	}

	@CacheEvict(cacheNames = "Countries")
	public void evictDataFromCache() {
		// TODO Auto-generated method stub

	}

}
