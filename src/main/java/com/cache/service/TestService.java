package com.cache.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {

	@Autowired
	private CacheManager cacheManager;

	public Object fetchAllContent() {
		log.info("{}", cacheManager instanceof ConcurrentMapCacheManager);
		return cacheManager.getCacheNames().stream()
				.collect(Collectors.toMap(x -> x.toUpperCase(), x -> cacheManager.getCache(x)));
	}

	@Cacheable("c1")
	public HashMap<Object, Object> fetchDataFromDB() {
		log.info("fetchDataFromDB() invoked --------------------");
		return new HashMap<>() {
			{
				put("KA", "KARNATAKA");
				put("TG", "TELANGANA");
				put("TN", "TAMILNADU");

			}
		};
	}
	

	@Cacheable("c2")
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

	@Cacheable("c3")
	public HashMap<Object, Object> fetchDataFrom3rdParty() {
		log.info("fetchDataFrom3rdParty() invoked --------------------");
		return new HashMap<>() {
			{
				put("SB", "SPRING BOOT");
				put("PY", "PYTHON");
			}
		};
	}
	
	@CachePut("c1")
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

	@CacheEvict(cacheNames = { "c1" })
	public void evictDataFromCache() {
		// TODO Auto-generated method stub

	}

}
