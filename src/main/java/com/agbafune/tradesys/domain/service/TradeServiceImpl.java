package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.exceptions.InsufficientFundsException;
import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.model.TradeAction;
import com.agbafune.tradesys.domain.model.TradeData;
import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.TradeRepository;
import com.agbafune.tradesys.infra.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TradeServiceImpl implements TradeService {

    private final AssetService assetService;
    private final UserService userService;
    private final PortfolioService portfolioService;
    private final TradeRepository tradeRepository;
    private final EventPublisher eventPublisher;

    private final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    public TradeServiceImpl(
            AssetService assetService,
            UserService userService,
            PortfolioService portfolioService,
            TradeRepository tradeRepository,
            EventPublisher eventPublisher
    ) {
        this.assetService = assetService;
        this.userService = userService;
        this.portfolioService = portfolioService;
        this.tradeRepository = tradeRepository;
        this.eventPublisher = eventPublisher;
    }

    public TradeData trade(Long userId, Long assetId, BigDecimal quantity, TradeAction action) {
        Asset asset = assetService.getAsset(assetId);

        switch (action) {
            case BUY -> buy(userId, asset, quantity);
            case SELL -> sell(userId, asset, quantity);
            default -> throw new IllegalArgumentException("Invalid trade action: " + action);
        }

        TradeData trade = save(userId, quantity, action, asset);
        eventPublisher.publishTradeEvent(userId, trade.id());

        return trade;
    }

    private void buy(Long userId, Asset asset, BigDecimal quantity) {
        BigDecimal assetPrice = asset.price().multiply(quantity);
        logger.info("Buying {} of asset {} at price {} for user {}", quantity, asset.symbol(), assetPrice, userId);

        UserLockManager.lockUser(userId);
        try {
            // check if user balance is enough
            User user = userService.getUserById(userId);
            if (user.funds().compareTo(assetPrice) < 0) {
                throw new InsufficientFundsException();
            }

            // decrease user balance
            userService.decreaseUserFunds(userId, assetPrice);

            // add asset to user portfolio
            Portfolio userPortfolio = portfolioService.getOrCreatePortfolioByUser(userId);
            userPortfolio.addAsset(asset, quantity);
            portfolioService.savePortfolio(userPortfolio);
        } finally {
            UserLockManager.unlockUser(userId);
        }
    }

    private void sell(Long userId, Asset asset, BigDecimal quantity) {
        BigDecimal assetPrice = asset.price().multiply(quantity);
        logger.info("Selling {} of asset {} at price {} for user {}", quantity, asset.symbol(), assetPrice, userId);

        // remove asset from user portfolio
        Portfolio userPortfolio = portfolioService.getPortfolioByUser(userId);
        userPortfolio.removeAsset(asset, quantity);
        portfolioService.savePortfolio(userPortfolio);

        // increase user balance
        userService.increaseUserFunds(userId, assetPrice);
    }

    private TradeData save(Long userId, BigDecimal quantity, TradeAction action, Asset asset) {
        return tradeRepository.save(
                new TradeData.Builder()
                        .userId(userId)
                        .assetId(asset.id())
                        .assetName(asset.name())
                        .assetSymbol(asset.symbol())
                        .assetPrice(asset.price())
                        .action(action.name())
                        .quantity(quantity)
                        .total(asset.price().multiply(quantity))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
