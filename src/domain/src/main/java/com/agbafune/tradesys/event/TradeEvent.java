package com.agbafune.tradesys.event;

public record TradeEvent(
    Long userId,
    Long assetId
) {
}
