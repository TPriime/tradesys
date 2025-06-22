package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.TradeData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeRepositoryImpl extends BaseInmemRepository<TradeData> implements TradeRepository {

    @Override
    public List<TradeData> findByUserId(Long userId) {
        return getValuesStream()
                .filter(trade -> trade.userId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Long getId(TradeData trade) {
        return trade.id();
    }

    @Override
    public TradeData setId(TradeData trade, Long id) {
        return new TradeData.Builder(trade).id(id).build();
    }

    @Override
    public String getName() {
        return "Trade";
    }
}
