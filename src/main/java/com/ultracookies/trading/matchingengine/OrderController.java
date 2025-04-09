package com.ultracookies.trading.matchingengine;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
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
        String msg;
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            msg = violations.iterator().next().getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
        if (!symbolRegistryService.symbolExists(order.getSymbol())) {
            msg = "Symbol " + order.getSymbol() + " does not exist.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
        order.setOrderId(UUID.randomUUID());
        orderBookService.publishOrder(order);
    }

}
