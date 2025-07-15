package com.agbafune.tradesys.handler.model;

import com.agbafune.tradesys.model.TradeData;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeDataBean(
        Long userId,
        Long assetId,
        String assetName,
        String assetSymbol,
        BigDecimal assetPrice,
        String action,
        BigDecimal quantity,
        BigDecimal total,
        LocalDateTime timestamp
) {
    public TradeDataBean(TradeData trade){
        this(
                trade.userId(),
                trade.assetId(),
                trade.assetName(),
                trade.assetSymbol(),
                trade.assetPrice(),
                trade.action(),
                trade.quantity(),
                trade.total(),
                trade.timestamp()
        );
    }
}