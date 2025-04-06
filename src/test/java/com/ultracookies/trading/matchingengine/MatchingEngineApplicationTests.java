package com.ultracookies.trading.matchingengine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MatchingEngineApplicationTests {

	@Autowired
	private OrderController orderController;

	@Test
	void contextLoads() {
		assertThat(orderController).isNotNull();
	}

}
