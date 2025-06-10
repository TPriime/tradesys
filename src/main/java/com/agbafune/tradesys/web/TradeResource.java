package com.agbafune.tradesys.web;

import com.agbafune.tradesys.domain.model.TradeAction;
import com.agbafune.tradesys.domain.model.TradeData;
import com.agbafune.tradesys.domain.repository.TradeRepository;
import com.agbafune.tradesys.domain.service.TradeService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/api/trades",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class TradeResource {

    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeRepository tradeRepository;

    @GetMapping()
    public List<TradeDataBean> getAllTrades(@RequestParam(required = false) Long userId) {
        List<TradeData> trades;

        if (userId != null) {
            trades = tradeRepository.findByUserId(userId);
        } else {
            trades = tradeRepository.findAll();
        }

        return trades.stream().map(TradeDataBean::new).toList();
    }

    @PostMapping("/buy")
    @ResponseStatus(HttpStatus.CREATED)
    public TradeDataBean buy(@RequestBody @Valid TradeInputBean input) {
        TradeData trade = tradeService.trade(input.userId(), input.assetId(), input.quantity(), TradeAction.BUY);
        return new TradeDataBean(trade);
    }

    @PostMapping("/sell")
    @ResponseStatus(HttpStatus.OK)
    public TradeDataBean sell(@RequestBody @Valid TradeInputBean input) {
        TradeData trade = tradeService.trade(input.userId(), input.assetId(), input.quantity(), TradeAction.SELL);
        return new TradeDataBean(trade);
    }
}
