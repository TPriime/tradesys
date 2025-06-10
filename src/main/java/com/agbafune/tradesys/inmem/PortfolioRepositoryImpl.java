package com.agbafune.tradesys.inmem;

import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
}
