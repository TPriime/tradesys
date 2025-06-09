package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.TradeAction;

public interface TradeService {
    void trade(String userId, String assetId, Double quantity, TradeAction action);
}
