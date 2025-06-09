package com.agbafune.tradesys;


import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TradeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testTradeForBuy() throws Exception {

        mockMvc.perform(post("/api/trades/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"asset\":\"BTC\", \"size\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testTradeForSell() throws Exception {

        mockMvc.perform(post("/api/trades/sell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"asset\":\"BTC\", \"size\":1}"))
                .andExpect(status().isOk());
    }
}