package com.fossil.tradeshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fossil.tradeshow")
public class TradeshowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeshowApplication.class, args);
	}

}
