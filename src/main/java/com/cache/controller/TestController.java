package com.cache.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cache.service.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;
	
	@Autowired
	private CacheManager cacheManager;

	@GetMapping("/allContent")
	public void fetchAllContent() {
		testService.fetchAllContent();
	}

	@GetMapping("/getData")
	public HashMap<Object, Object> getCacheData() {
		return new HashMap<>() {
			{
				putAll(testService.fetchDataFromDB());
				putAll(testService.fetchDataFromDownStream());
				putAll(testService.fetchDataFrom3rdParty());
			}
		};
	}

	@GetMapping("/evictCache")
	public void evictCacheData() {
		testService.evictDataFromCache();
	}
	
	@GetMapping("/refreshCache")
	public void refreshCache() {
		cacheManager.getCache("Countries").clear();
		testService.fetchDataFromDB();
		
	}
	
	@GetMapping("/refreshCache2")
	public void refreshCacheUsingCachePut() {
		testService.fetchDataFromDBAlways();
		
	}

}
