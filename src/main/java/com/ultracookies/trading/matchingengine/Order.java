package com.ultracookies.trading.matchingengine;

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

    private final OrderType orderType;
    private final OrderSide orderSide;

    @NotBlank(message = "Ticker symbol is required")
    private final String symbol;

    @NotNull(message = "Quantity is required (>= 1)")
    @Min(value = 1, message = "Quantity must be at least 1")
    private final Integer quantity;

    @NotNull(message = "Price is required (>= 0.01)")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
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
