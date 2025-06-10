package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.exceptions.AssetNotFoundException;
import com.agbafune.tradesys.domain.exceptions.PortfolioNotFoundException;
import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Portfolio getPortfolioByUser(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new PortfolioNotFoundException(userId));
    }

    public Portfolio getOrCreatePortfolioByUser(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElse(portfolioRepository.save(new Portfolio(null, userId, new ArrayList<>())));
    }

    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }
}
