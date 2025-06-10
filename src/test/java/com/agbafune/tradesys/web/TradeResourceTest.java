package com.agbafune.tradesys.web;


import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TradeResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoSpyBean
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final long testUserId = 1;
    private final long testAssetId = 1;

    @BeforeEach
    void setUp() {
        userRepository.save(new User.Builder()
                .id(testUserId)
                .username("testUser")
                .funds(new BigDecimal("1000.00"))
                .build());


    }

    @Test
    void shouldBuyAsset() throws Exception {
        performBuy(testAssetId, "2.5")
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotBuyWithInsufficientFunds() throws Exception {
        // Set up a user with insufficient funds
        User user = new User.Builder().funds(BigDecimal.ZERO).build();
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(user));

        // Attempt to perform a buy trade
        performBuy(testAssetId, "10.0")
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("Insufficient funds to trade asset."));

        // Verify that the repository was called
        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void shouldSellObtainedAsset() throws Exception {
        performBuy(testAssetId, "10.0");

        performSell(testAssetId, "1.0")
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSellMoreThanObtainedAsset() throws Exception {
        performBuy(testAssetId, "10.0");
        BigDecimal fundBeforeSellAttempt = userRepository.findById(testUserId).orElseThrow().funds();

        performSell(testAssetId, "100.0")
                .andExpect(status().isForbidden());

        BigDecimal finalFunds = userRepository.findById(testUserId).orElseThrow().funds();
        assertEquals(finalFunds, fundBeforeSellAttempt); // Funds should not change
    }

    @Test
    void shouldNotSellAssetNotPurchased() throws Exception {
        performBuy(testAssetId, "10.0");
        BigDecimal fundBeforeSellAttempt = userRepository.findById(testUserId).orElseThrow().funds();

        long someOtherAssetId = 7L;
        performSell(someOtherAssetId, "1.0")
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Asset with ID 7 not found."));

        BigDecimal finalFunds = userRepository.findById(testUserId).orElseThrow().funds();
        assertEquals(finalFunds, fundBeforeSellAttempt); // Funds should not change
    }

    @Test
    void shouldNotSellWithoutAPortfolio() throws Exception {
        performSell(testAssetId, "1.0")
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                    .value("Portfolio not found for user ID: 1. Buy an asset to create one."));
    }



    private ResultActions performBuy(long assetId, String quantity) throws Exception {
        return mockMvc.perform(post("/api/trades/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(Map.of(
                                "assetId", assetId,
                                "quantity", quantity,
                                "userId", testUserId
                        ))));
    }

    private ResultActions performSell(long assetId, String quantity) throws Exception {
        return mockMvc.perform(post("/api/trades/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(Map.of(
                                "assetId", assetId,
                                "quantity", quantity,
                                "userId", testUserId
                        ))));
    }
}