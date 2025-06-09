package com.agbafune.tradesys.config;

import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.repository.AssetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private final AssetRepository assetRepository;

    public DataInitializer(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public void run(String... args) {
       assetRepository.save(new Asset.Builder()
               .name("Bitcoin")
               .symbol("BTC")
               .quantity(100)
               .price(new BigDecimal("50.00"))
               .build()
       );

       assetRepository.save(new Asset.Builder()
               .name("Etereum")
               .symbol("ETH")
               .quantity(100)
               .price(new BigDecimal("10.00"))
               .build()
       );

        assetRepository.save(new Asset.Builder()
                .name("Litecoin")
                .symbol("LTC")
                .quantity(100)
                .price(new BigDecimal("5.00"))
                .build()
        );
    }
}
