package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.TradeData;

import java.util.List;

public interface TradeRepository extends BaseRepository<TradeData> {
    List<TradeData> findByUserId(Long userId);
}
