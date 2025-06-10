package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.TradeAction;
import com.agbafune.tradesys.domain.model.TradeData;

import java.math.BigDecimal;

public interface TradeService {
    TradeData trade(Long userId, Long assetId, BigDecimal quantity, TradeAction action);
}
