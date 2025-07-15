package com.agbafune.tradesys.web;

import com.agbafune.tradesys.model.TradeAction;
import com.agbafune.tradesys.model.TradeData;
import com.agbafune.tradesys.repository.TradeRepository;
import com.agbafune.tradesys.api.SystemTrader;
import com.agbafune.tradesys.web.model.TradeDataBean;
import com.agbafune.tradesys.web.model.TradeInputBean;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/trades",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class TradeResource {

    @Autowired
    private SystemTrader systemTrader;
    @Autowired
    private TradeRepository tradeRepository;

    @GetMapping()
    public List<TradeDataBean> getAllTrades(@RequestParam(required = false) Long userId) {
        List<TradeData> trades = userId != null
            ? tradeRepository.findByUserId(userId)
            : tradeRepository.findAll();
        return trades.stream().map(TradeDataBean::new).toList();
    }

    @PostMapping("/buy")
    @ResponseStatus(HttpStatus.CREATED)
    public TradeDataBean buy(@RequestBody @Valid TradeInputBean input) {
        TradeData trade = systemTrader.submitTrade(input.userId(), input.assetId(), input.quantity(), TradeAction.BUY);
        return new TradeDataBean(trade);
    }

    @PostMapping("/sell")
    @ResponseStatus(HttpStatus.OK)
    public TradeDataBean sell(@RequestBody @Valid TradeInputBean input) {
        TradeData trade = systemTrader.submitTrade(input.userId(), input.assetId(), input.quantity(), TradeAction.SELL);
        return new TradeDataBean(trade);
    }
}
