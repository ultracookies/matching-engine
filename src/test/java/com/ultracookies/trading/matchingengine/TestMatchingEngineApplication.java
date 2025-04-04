package com.ultracookies.trading.matchingengine;

import org.springframework.boot.SpringApplication;

public class TestMatchingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.from(MatchingEngineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
