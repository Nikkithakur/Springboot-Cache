package com.cache;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {

	@Autowired
	private CacheManager cacheManager;

	public HashMap<Object, Object> getRefData() {
		// TODO Auto-generated method stub
		log.info("TestService:getRefData() invoked......");
		HashMap<Object, Object> map = new HashMap<>() {
			{
				put("IN", "INDIA");
				put("USA", "UNITED STATES OF AMERICA");
				put("GE", "GERMANY");
				put("SL", "SRI LANKA");
			}
		};
		return map;
	}

	@Cacheable("refdata")
	public HashMap<Object, Object> getRefDataFromCache() {
		log.info("TestService:getRefDataFromCache() invoked......");
		HashMap<Object, Object> map = new HashMap<>() {
			{
				put("IN", "INDIA");
				put("USA", "UNITED STATES OF AMERICA");
				put("GE", "GERMANY");
				put("SL", "SRI LANKA");
			}
		};
		return map;
	}

	public Object getDataFromCache() {
		log.info("{}", cacheManager instanceof ConcurrentMapCacheManager);
		return cacheManager.getCacheNames()
				.stream()
				.collect(Collectors.toMap( x-> x.toUpperCase(), x -> cacheManager.getCache(x)));
	}

}
