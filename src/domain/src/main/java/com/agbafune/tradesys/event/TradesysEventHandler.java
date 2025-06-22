package com.agbafune.tradesys.event;

import com.agbafune.tradesys.UserRankManager;
import com.agbafune.tradesys.SystemRewarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TradesysEventHandler {

    private final SystemRewarder systemRewarder;
    private final UserRankManager userRankManager;
    private final Logger logger = LoggerFactory.getLogger(TradesysEventHandler.class);

    public TradesysEventHandler(SystemRewarder systemRewarder, UserRankManager userRankManager) {
        this.systemRewarder = systemRewarder;
        this.userRankManager = userRankManager;
    }

    @EventListener
    public void handleTradeEvent(TradeEvent event) {
        logger.info("Received trade event for user ID: {}, asset ID: {}",
                    event.userId(), event.assetId());
        systemRewarder.rewardForTrade(event.userId(), event.assetId());
    }

    @EventListener
    public void handleUserRewardEvent(UserRewardEvent event) {
        logger.info("Received user reward event for user ID: {}, awarded gems: {}, total gems: {}",
                    event.userId(), event.awardedGems(), event.userGems());

        userRankManager.updateRank(event.userId(), event.userGems());
    }
}
