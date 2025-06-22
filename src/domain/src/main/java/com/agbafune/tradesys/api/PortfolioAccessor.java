package com.agbafune.tradesys.api;

import com.agbafune.tradesys.model.Portfolio;

public interface PortfolioAccessor {
    Portfolio getPortfolioByUser(Long userId);
}
