package com.ultracookies.trading.matchingengine;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    private final OrderBookService orderBookService;

    public OrderController(OrderBookService orderBookService) { this.orderBookService = orderBookService; }

    @PostMapping(value = "/api/orders")
    public void processOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID());
        order.setOrderType(OrderType.LIMIT);
        orderBookService.publishOrder(order);
    }
}
