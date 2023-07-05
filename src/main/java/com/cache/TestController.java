package com.cache;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private TestService testService;
	
	
	@GetMapping("/getRef")
	public HashMap<Object, Object> getRefData(){
	 return testService.getRefData();
	}
	
	@GetMapping("/getRefFromCache")
	public HashMap<Object, Object> getRefDataFromCache(){
	 return testService.getRefDataFromCache();
	}
	
	@GetMapping("/fetchCacheData")
	public Object fetchCacheData(){
	 return testService.getDataFromCache();
	}

}
