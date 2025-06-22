package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.Portfolio;

import java.util.Optional;

public interface PortfolioRepository extends BaseRepository<Portfolio>  {
    Optional<Portfolio> findByUserId(Long userId);
}
