package com.agbafune.tradesys.api;

import com.agbafune.tradesys.model.TradeAction;
import com.agbafune.tradesys.model.TradeData;

import java.math.BigDecimal;

public interface SystemTrader {
    TradeData submitTrade(Long userId, Long assetId, BigDecimal quantity, TradeAction action);
}
