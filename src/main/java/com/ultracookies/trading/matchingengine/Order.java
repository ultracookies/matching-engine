package com.ultracookies.trading.matchingengine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Order {
    private UUID orderId;
    private final Instant timeReceived;

    @NotBlank
    private final String symbol;

    @NotNull
    private final OrderType orderType;

    @NotNull
    private final OrderSide orderSide;

    @Min(value = 1, message = "Quantity must be at least 1")
    private final Integer quantity;

    @DecimalMin(value = "0.01", message = "Price must be at least one cent")
    private final BigDecimal price;

    public Order(String symbol, OrderType orderType,
                 OrderSide orderSide, Instant timeReceived,
                 Integer quantity, BigDecimal price)
    {
        this.symbol = symbol;
        this.orderType = orderType;
        this.orderSide = orderSide;
        this.timeReceived = timeReceived;
        this.quantity = quantity;
        this.price = price;
    }

    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public String getSymbol() { return symbol; }
}
