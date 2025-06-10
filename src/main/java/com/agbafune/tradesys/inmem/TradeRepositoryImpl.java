package com.agbafune.tradesys.inmem;

import com.agbafune.tradesys.domain.model.TradeData;
import com.agbafune.tradesys.domain.repository.TradeRepository;
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
}
