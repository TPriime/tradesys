package com.agbafune.tradesys.config;

import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.AssetRepository;
import com.agbafune.tradesys.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    @Override
    public void run(String... args) {
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
