package com.agbafune.tradesys.web.model;

import com.agbafune.tradesys.model.Asset;

import java.math.BigDecimal;

public record AssetBean(
        Long assetId,
        String name,
        String symbol,
        BigDecimal price
) {
    public AssetBean(Asset a)  {
        this(
                a.id(),
                a.name(),
                a.symbol(),
                a.price()
        );
    }
}
