package com.agbafune.tradesys.web.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TradeInputBean(
        long userId,
        long assetId,
        @NotNull @Positive(message = "Quantity must be positive")
        BigDecimal quantity
) {

}
