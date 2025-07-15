package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.Portfolio;

import java.util.Optional;

public class PortfolioRepositoryImpl extends BaseInmemRepository<Portfolio> implements PortfolioRepository {
    @Override
    public Optional<Portfolio> findByUserId(Long userId) {
        return getValuesStream()
                .filter(portfolio -> portfolio.userId().equals(userId))
                .findFirst();
    }

    @Override
    public Long getId(Portfolio portfolio) {
        return portfolio.id();
    }

    @Override
    public Portfolio setId(Portfolio portfolio, Long id) {
        return new Portfolio(id, portfolio.userId(), portfolio.assets());
    }

    @Override
    public String getName() {
        return "Portfolio";
    }
}
