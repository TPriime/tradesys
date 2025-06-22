package com.agbafune.tradesys.simulator;

import com.agbafune.tradesys.api.AssetChangeSimulator;
import com.agbafune.tradesys.model.Asset;
import com.agbafune.tradesys.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Component
public class AssetSimulator implements AssetChangeSimulator {
    @Autowired
    private AssetRepository assetRepository;

    private final Logger logger = LoggerFactory.getLogger(AssetSimulator.class);

    @Override
    public void updateAssetPrices() {
        logger.info("Updating asset prices...");

        assetRepository.findAll().forEach(
            asset -> {
                // Simulate price change
                double priceChange = Math.random() * 3.5; // Random change between 0% and 1.5%
                BigDecimal newPrice = asset.price().add(BigDecimal.valueOf(priceChange))
                        .setScale(4, RoundingMode.HALF_UP);

                Asset updated = new Asset.Builder(asset)
                        .price(newPrice)
                        .lastUpdated(LocalDateTime.now())
                        .build();

                assetRepository.save(updated);
            }
        );
    }
}
