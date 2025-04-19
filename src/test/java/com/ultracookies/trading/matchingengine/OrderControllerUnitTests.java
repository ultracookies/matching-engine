package com.ultracookies.trading.matchingengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderBookService orderBookService;

    @MockitoBean
    private SymbolRegistryService symbolRegistryService;

    @Autowired
    private ObjectMapper objectMapper;

    // TODO test for multiple violations simultaneously

//    @Test
//    void checkForInvalidOrderTypeSide() throws Exception {
//        var body = Map.of(
//                "orderType", "LI",
//                "orderSide", "B",
//                "symbol", "AAPL",
//                "quantity", "100",
//                "price", "200.17"
//        );
//
//        List<String> violations = Arrays.asList(
//                "Invalid value for orderType. Expected one of: [LIMIT]",
//                "Invalid value for orderSide. Expected one of: [BUY, SELL]"
//        );
//
//        var result = mockMvc.perform(post("/api/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(body)))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
//                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
//                .andExpect(jsonPath("$.detail", is(2 + " violation(s).")))
//                .andExpect(jsonPath("$.violations", containsInAnyOrder(violations.toArray())))
//                .andReturn();
//
//        Assertions.assertInstanceOf(HttpMessageNotReadableException.class, result.getResolvedException());
//    }

    @Test
    void checkForInvalidSymbolQuantityPrice() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "",
                "quantity", "0",
                "price", "0"
        );

        List<String> violations = Arrays.asList(
                "Ticker symbol is required",
                "Quantity must be at least 1",
                "Price must be at least 0.01"
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(3 + " violation(s).")))
                .andExpect(jsonPath("$.violations", containsInAnyOrder(violations.toArray())))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForNullSymbolQuantityPrice() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "",
                "quantity", "",
                "price", ""
        );

        List<String> violations = Arrays.asList(
                "Ticker symbol is required",
                "Quantity is required (>= 1)",
                "Price is required (>= 0.01)"
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(3 + " violation(s).")))
                .andExpect(jsonPath("$.violations", containsInAnyOrder(violations.toArray())))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForInvalidOrderSide() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "",
                "symbol", "AAPL",
                "quantity", 1,
                "price", 0.01
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is("Invalid value for orderSide. Expected one of: [BUY, SELL]")))
                .andReturn();

        Assertions.assertInstanceOf(HttpMessageNotReadableException.class, result.getResolvedException());
    }

    @Test
    void checkForInvalidOrderType() throws Exception {
        var body = Map.of(
                "orderType", "LI",
                "orderSide", "BUY",
                "symbol", "AAPL",
                "quantity", 1,
                "price", 0.01
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is("Invalid value for orderType. Expected one of: [LIMIT]")))
                .andReturn();

        Assertions.assertInstanceOf(HttpMessageNotReadableException.class, result.getResolvedException());
    }

    @Test
    void checkForBlankSymbol() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "",
                "quantity", 1,
                "price", 0.01
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(1 + " violation(s).")))
                .andExpect(jsonPath("$.violations[0]", is("Ticker symbol is required")))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForInvalidPrice() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "AAPL",
                "quantity", 1,
                "price", 0
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(1 + " violation(s).")))
                .andExpect(jsonPath("$.violations[0]", is("Price must be at least 0.01")))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForNullPrice() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "AAPL",
                "quantity", 1,
                "price", ""
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(1 + " violation(s).")))
                .andExpect(jsonPath("$.violations[0]", is("Price is required (>= 0.01)")))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForInvalidQuantity() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "AAPL",
                "quantity", 0,
                "price", 200
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(1 + " violation(s).")))
                .andExpect(jsonPath("$.violations[0]", is("Quantity must be at least 1")))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForNullQuantity() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "AAPL",
                "quantity", "",
                "price", 200
        );

        var result = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Invalid order parameter(s)")))
                .andExpect(jsonPath("$.detail", is(1 + " violation(s).")))
                .andExpect(jsonPath("$.violations[0]", is("Quantity is required (>= 1)")))
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
    }

    @Test
    void checkForCorrectOrder() throws Exception {
        var body = Map.of(
                "orderType", "LIMIT",
                "orderSide", "BUY",
                "symbol", "AAPL",
                "quantity", "10",
                "price", "200"
        );

        mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

}
