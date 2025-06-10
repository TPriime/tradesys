package com.agbafune.tradesys.web;


import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;
import com.agbafune.tradesys.domain.service.system.Rewarder;
import com.agbafune.tradesys.domain.service.system.RewarderImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TradeAwardsTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Rewarder rewarder;
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
        ((RewarderImpl) rewarder).reset();
    }

    @Test
    void shouldReceiveOneGemAfterEachBuy() throws Exception {
        int initialGemCount = userRepository.findById(testUserId).orElseThrow().gemCount();
        performBuy(testAssetId, "2.5")
                .andExpect(status().isCreated());

        // allow events propagate
        Thread.sleep(100);

        int finalGemCount = userRepository.findById(testAssetId).orElseThrow().gemCount();
        assertEquals(finalGemCount, initialGemCount + 1);
    }

    @Test
    void shouldReceive2GemsABuyAndTrade() throws Exception {
        int initialGemCount = userRepository.findById(testUserId).orElseThrow().gemCount();
        performBuy(testAssetId, "2.5");
        performSell(testAssetId, "2.5").andExpect(status().isOk());

        // allow events propagate
        Thread.sleep(100);

        int finalGemCount = userRepository.findById(testAssetId).orElseThrow().gemCount();
        assertEquals(finalGemCount, initialGemCount + 2);
    }

    @Test
    void shouldReceive3ExtraStreakGemsAfter3Trades() throws Exception {
        int initialGemCount = userRepository.findById(testUserId).orElseThrow().gemCount();
        for (int i = 1; i <= 3; i++) {
            performBuy(testAssetId, "2.5")
                    .andExpect(status().isCreated());
        }

        // allow events propagate
        Thread.sleep(100);

        int expectedStreakBonus = 3;
        int finalGemCount = userRepository.findById(testAssetId).orElseThrow().gemCount();
        assertEquals(finalGemCount, initialGemCount + 3 + expectedStreakBonus);
    }

    @Test
    void shouldReceive5ExtraMilestoneGemsAfter5Trades() throws Exception {
        int initialGemCount = userRepository.findById(testUserId).orElseThrow().gemCount();
        for (int i = 1; i <= 5; i++) {
            performBuy(testAssetId, "2.5")
                    .andExpect(status().isCreated());
        }

        // allow events propagate
        Thread.sleep(100);

        int expectedStreakBonus = 3;
        int expectedMilestoneBonus = 5;
        int finalGemCount = userRepository.findById(testAssetId).orElseThrow().gemCount();
        assertEquals(finalGemCount, initialGemCount + 5 + expectedStreakBonus + expectedMilestoneBonus);
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