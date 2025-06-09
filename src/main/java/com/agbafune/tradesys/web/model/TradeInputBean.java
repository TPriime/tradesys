package com.agbafune.tradesys.web.model;

import java.math.BigDecimal;

public record TradeInputBean(
        Long userId,
        Long assetId,
        Double quantity
) {

}
