package com.agbafune.tradesys;

import com.agbafune.tradesys.repository.AssetRepository;
import com.agbafune.tradesys.repository.AssetRepositoryImpl;
import com.agbafune.tradesys.repository.PortfolioRepository;
import com.agbafune.tradesys.repository.PortfolioRepositoryImpl;
import com.agbafune.tradesys.repository.TradeRepository;
import com.agbafune.tradesys.repository.TradeRepositoryImpl;
import com.agbafune.tradesys.repository.UserRepository;
import com.agbafune.tradesys.repository.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AssetRepository getAssetRepository() {
        return new AssetRepositoryImpl();
    }

    @Bean
    public PortfolioRepository getPortfolioRepository() {
        return new PortfolioRepositoryImpl();
    }

    @Bean
    public TradeRepository getTradeRepository() {
        return new TradeRepositoryImpl();
    }

    @Bean
    public UserRepository getUserRepository(){
        return new UserRepositoryImpl();
    }
}
