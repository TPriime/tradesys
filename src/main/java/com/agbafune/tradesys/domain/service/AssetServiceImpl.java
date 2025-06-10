package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.exceptions.AssetNotFoundException;
import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public Asset getAsset(Long assetId) {
        return assetRepository.findById(assetId).orElseThrow(
                () -> new AssetNotFoundException(assetId)
        );
    }

    @Override
    public void updateAssetPrice(Long assetId, BigDecimal newPrice) {
        assetRepository.findById(assetId)
                .ifPresent(asset -> assetRepository.save(
                        new Asset.Builder(asset)
                                .price(newPrice)
                                .build()
                ));
    }
}
