package com.ultracookies.trading.matchingengine;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class OrderController {

    private final OrderBookService orderBookService;
    private final SymbolRegistryService symbolRegistryService;
    private final Validator validator;

    public OrderController(OrderBookService orderBookService,
                           SymbolRegistryService symbolRegistryService,
                           Validator validator)
    {
        this.orderBookService = orderBookService;
        this.symbolRegistryService = symbolRegistryService;
        this.validator = validator;
    }

    @PostMapping(value = "/api/orders")
    public void processOrder(@Valid @RequestBody Order order) {
        order.setOrderId(UUID.randomUUID());
        orderBookService.publishOrder(order);
    }

    private void validateOrder(Order order) {
        var violations = validator.validate(order);
        String msg;
        if (!violations.isEmpty()) {
            msg = "Incomplete order body.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
        if (!symbolRegistryService.symbolExists(order.getSymbol())) {
            msg = "Symbol " + order.getSymbol() + " does not exist.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

}
