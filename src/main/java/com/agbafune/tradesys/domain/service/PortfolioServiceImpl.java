package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.exceptions.PortfolioNotFoundException;
import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.repository.AssetRepository;
import com.agbafune.tradesys.domain.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, AssetRepository assetRepository) {
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
