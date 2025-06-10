package com.agbafune.tradesys.config;

import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.AssetRepository;
import com.agbafune.tradesys.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public DataInitializer(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
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

        userRepository.save(new User.Builder()
                .username("admin")
                .gemCount(100)
                .rank(1)
                .funds(new BigDecimal("1000.00"))
                .build()
        );
    }
}
