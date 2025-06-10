package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.Portfolio;

public interface PortfolioService {
    public Portfolio getPortfolioByUser(Long userId);
    public Portfolio getOrCreatePortfolioByUser(Long userId);
    public Portfolio savePortfolio(Portfolio portfolio);
}
