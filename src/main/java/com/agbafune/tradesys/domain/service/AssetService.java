package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.Asset;

import java.math.BigDecimal;

public interface AssetService {
    public Asset getAsset(Long assetId);
    public void updateAssetPrice(Long assetId, BigDecimal newPrice);
}
