package com.ultracookies.trading.matchingengine;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class OrderController {

    private final OrderBookService orderBookService;
    private final SymbolRegistryService symbolRegistryService;

    public OrderController(OrderBookService orderBookService,
                           SymbolRegistryService symbolRegistryService)
    {
        this.orderBookService = orderBookService;
        this.symbolRegistryService = symbolRegistryService;
    }

    @PostMapping(value = "/api/orders")
    public void processOrder(@Valid @RequestBody Order order) {
        order.setOrderId(UUID.randomUUID());
        if (!symbolRegistryService.symbolExists(order.getSymbol()))
            throw new NonExistentTicker("Security with " + order.getSymbol() + " does not exist");

        orderBookService.publishOrder(order);
    }

}
