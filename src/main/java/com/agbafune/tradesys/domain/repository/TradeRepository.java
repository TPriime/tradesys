package com.agbafune.tradesys.domain.repository;

import com.agbafune.tradesys.domain.model.TradeData;

import java.util.List;

public interface TradeRepository {
    List<TradeData> findByUserId(Long userId);
    List<TradeData> findAll();
    TradeData save(TradeData trade);
}
