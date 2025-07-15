package com.agbafune.tradesys.config;

import com.agbafune.tradesys.model.Asset;
import com.agbafune.tradesys.model.User;
import com.agbafune.tradesys.repository.AssetRepository;
import com.agbafune.tradesys.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DataInitializer {
    private final static Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    public static void initialize(AssetRepository assetRepository, UserRepository userRepository) {
        logger.info("Creating default admin user and assets...");

        userRepository.save(new User.Builder()
                .username("admin")
                .gemCount(0)
                .funds(new BigDecimal("1000.00"))
                .build()
        );

        assetRepository.save(new Asset.Builder()
                .name("Bitcoin")
                .symbol("BTC")
                .price(new BigDecimal("50.00"))
                .build()
        );

        assetRepository.save(new Asset.Builder()
                .name("Etereum")
                .symbol("ETH")
                .price(new BigDecimal("10.00"))
                .build()
        );

        assetRepository.save(new Asset.Builder()
                .name("Litecoin")
                .symbol("LTC")
                .price(new BigDecimal("5.00"))
                .build()
        );
    }
}
