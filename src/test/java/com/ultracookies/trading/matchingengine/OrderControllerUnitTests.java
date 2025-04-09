package com.ultracookies.trading.matchingengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderBookService orderBookService;

    @MockitoBean
    private SymbolRegistryService symbolRegistryService;

    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void checkIfResponseStatusExceptionThrownForInvalidTicker() {

    }

    @Test
    void checkIfCorrectInputReturnsOKHttpStatus() throws Exception {
        Mockito.when(symbolRegistryService.symbolExists(Mockito.anyString())).thenReturn(true);

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
