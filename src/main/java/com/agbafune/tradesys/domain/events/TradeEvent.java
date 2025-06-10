package com.agbafune.tradesys.domain.events;

public record TradeEvent(
    Long userId,
    Long assetId
) {
}
