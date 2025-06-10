package com.agbafune.tradesys.domain.service.system;

public interface Rewarder {
    public void rewardForTrade(Long userId, Long tradeId);
}
