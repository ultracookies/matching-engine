package com.ultracookies.trading.matchingengine;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderValidationUnitTest {

    private Validator validator;

    @BeforeAll
    public void setUp() {
        var validationFactory = Validation.buildDefaultValidatorFactory();
        validator = validationFactory.getValidator();
    }

    @Test
    void checkIfZeroQuantityIsInvalid() {
        var symbol = "AAPL";
        var orderType = OrderType.LIMIT;
        var orderSide = OrderSide.BUY;
        var instant = Instant.MIN;
        var quantity = 0;
        var price = BigDecimal.valueOf(200);

        var order = new Order(symbol, orderType, orderSide, instant, quantity, price);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        var actualViolationMsg = violations.iterator().next().getMessage();

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Quantity must be at least 1.", actualViolationMsg);

    }

    @Test
    void testValidOrder() {
        var symbol = "AAPL";
        var orderType = OrderType.LIMIT;
        var orderSide = OrderSide.BUY;
        var instant = Instant.MIN;
        var quantity = 1;
        var price = BigDecimal.valueOf(200);

        var order = new Order(symbol, orderType, orderSide, instant, quantity, price);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        Assertions.assertTrue(violations.isEmpty());
    }
}
