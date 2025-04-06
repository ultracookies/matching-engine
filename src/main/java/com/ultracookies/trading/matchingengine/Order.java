package com.ultracookies.trading.matchingengine;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Order {
    private UUID orderId;
    private OrderType orderType;

    @NonNull private OrderSide orderSide;
    @NonNull private String symbol;
    @NonNull private BigDecimal price;
    @NonNull private Integer quantity;
    @NonNull private Instant timeReceived;

    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public void setOrderId(UUID orderId) { this.orderId = orderId; }
}
