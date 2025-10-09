package com.sayjong.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
	@GetMapping()
	public ResponseEntity<?> sayHello() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Hello, Sayjong Backend is running successfully!");
		return ResponseEntity.ok(response);
	}
}
