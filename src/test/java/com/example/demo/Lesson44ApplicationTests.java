package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Lesson44ApplicationTests {
	
	private PasswordEncoder passwordEncoder;
	
    @Autowired
	public Lesson44ApplicationTests(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	@Test
	void test() {
		String encoded=passwordEncoder.encode("petr");
		System.out.println(encoded );
		
	}

}
