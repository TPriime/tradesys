package com.agbafune.tradesys;

import com.agbafune.tradesys.event.EventPublisher;
import com.agbafune.tradesys.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SystemRewarder {

    private final UserService userService;
    private final EventPublisher eventPublisher;

    private final Map<Long, Integer> userTrades = new ConcurrentHashMap<>();
    private Long lastTraderId = -1L;
    private int tradeStreak = 0;


    public SystemRewarder(UserService userService, EventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    public void rewardForTrade(Long userId, Long tradeId) {
        int rewardPoints = 1; // Default reward points per trade
        rewardPoints += calculateMileStoneBonus(userId);
        rewardPoints += calculateStreakBonus(userId);

        User user = userService.increaseUserGems(userId, rewardPoints);
        eventPublisher.publishUserRewardedEvent(userId, rewardPoints, user.gemCount());
    }

    private int calculateMileStoneBonus(Long userId) {
        int tradeCount = increaseAndGetTradeCount(userId);

        return switch (tradeCount) {
            case 5 ->  5; // Bonus for a milestone of 5 trades
            case 10 ->  10; // Bonus for a milestone of 10 trades
            // Bonus for any milestone in series of twenty, or no bonus
            default -> tradeStreak % 20 == 0 ? tradeStreak : 0;
        };
    }

    private Integer increaseAndGetTradeCount(Long userId) {
        return userTrades.merge(userId, 1, Integer::sum);
    }

    private synchronized Integer calculateStreakBonus(Long userId) {
        if (userId.equals(lastTraderId)) {
            tradeStreak++;
        } else {
            // Reset streak if the user is different
            lastTraderId = userId;
            tradeStreak = 1;
        }

        return switch (tradeStreak) {
            case 3 -> 3; // Bonus for 3 trades in a row
            case 7 -> 7; // Bonus for 7 trades in a row
            // Bonus for more than 7 trades in a row else no bonus
            default -> tradeStreak > 7 ? tradeStreak : 0;
        };
    }

    public void reset(){
        userTrades.clear();
        lastTraderId = -1L;
        tradeStreak = 0;
    }
}
