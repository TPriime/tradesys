package com.agbafune.tradesys.domain.repository;

import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository {
    Optional<Portfolio> findByUserId(Long userId);
    List<Portfolio> findAll();
    Portfolio save(Portfolio portfolio);
}
