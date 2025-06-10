package com.agbafune.tradesys.infra;

import com.agbafune.tradesys.domain.events.TradeEvent;
import com.agbafune.tradesys.domain.events.UserRewardEvent;
import com.agbafune.tradesys.domain.service.system.Ranker;
import com.agbafune.tradesys.domain.service.system.Rewarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TradesysEventListener {

    private final Rewarder rewarder;
    private final Ranker ranker;
    private final Logger logger = LoggerFactory.getLogger(TradesysEventListener.class);

    public TradesysEventListener(Rewarder rewarder, Ranker ranker) {
        this.rewarder = rewarder;
        this.ranker = ranker;
    }

    @EventListener
    public void handleTradeEvent(TradeEvent event) {
        logger.info("Received trade event for user ID: {}, asset ID: {}",
                    event.userId(), event.assetId());
        rewarder.rewardForTrade(event.userId(), event.assetId());
    }

    @EventListener
    public void handleUserRewardEvent(UserRewardEvent event) {
        logger.info("Received user reward event for user ID: {}, awarded gems: {}, total gems: {}",
                    event.userId(), event.awardedGems(), event.userGems());

        ranker.updateRank(event.userId(), event.userGems());
    }
}
