package com.ultracookies.trading.matchingengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderControllerUnitTests {

    @Mock
    private OrderBookService orderBookService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testProcessReceivesCorrectOrder() {

    }
}
