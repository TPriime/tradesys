package com.agbafune.tradesys;

import com.agbafune.tradesys.exception.PortfolioNotFoundException;
import com.agbafune.tradesys.model.Portfolio;
import com.agbafune.tradesys.repository.AssetRepository;
import com.agbafune.tradesys.repository.PortfolioRepository;
import com.agbafune.tradesys.api.PortfolioAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PortfolioManager implements PortfolioAccessor {
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public PortfolioManager(PortfolioRepository portfolioRepository, AssetRepository assetRepository) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public Portfolio getPortfolioByUser(Long userId) {
         Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new PortfolioNotFoundException(userId));
         portfolio.loadAssets(assetId -> assetRepository.findById(assetId).orElseThrow());
         return portfolio;
    }

    public Portfolio getOrCreatePortfolioByUser(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElse(portfolioRepository.save(new Portfolio(null, userId, new ArrayList<>())));
    }

    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }
}
