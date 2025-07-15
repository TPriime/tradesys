package com.agbafune.tradesys;

import com.agbafune.tradesys.exception.InsufficientFundsException;
import com.agbafune.tradesys.event.AppEventPublisher;
import com.agbafune.tradesys.model.Asset;
import com.agbafune.tradesys.model.Portfolio;
import com.agbafune.tradesys.model.TradeAction;
import com.agbafune.tradesys.model.TradeData;
import com.agbafune.tradesys.model.User;
import com.agbafune.tradesys.repository.AssetRepository;
import com.agbafune.tradesys.repository.TradeRepository;
import com.agbafune.tradesys.api.SystemTrader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SystemTraderImpl implements SystemTrader {

    private final AssetRepository assetRepository;
    private final UserService userService;
    private final PortfolioManager portfolioManager;
    private final TradeRepository tradeRepository;
    private final AppEventPublisher eventPublisher;

    private final Logger logger = LoggerFactory.getLogger(SystemTraderImpl.class);

    public SystemTraderImpl(
            AssetRepository assetRepository,
            UserService userService,
            PortfolioManager portfolioManager,
            TradeRepository tradeRepository,
            AppEventPublisher eventPublisher
    ) {
        this.assetRepository = assetRepository;
        this.userService = userService;
        this.portfolioManager = portfolioManager;
        this.tradeRepository = tradeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public TradeData submitTrade(Long userId, Long assetId, BigDecimal quantity, TradeAction action) {
        Asset asset = assetRepository.getById(assetId);

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
            Portfolio userPortfolio = portfolioManager.getOrCreatePortfolioByUser(userId);
            userPortfolio.addAsset(asset, quantity);
            portfolioManager.savePortfolio(userPortfolio);
        } finally {
            UserLockManager.unlockUser(userId);
        }
    }

    private void sell(Long userId, Asset asset, BigDecimal quantity) {
        BigDecimal assetPrice = asset.price().multiply(quantity);
        logger.info("Selling {} of asset {} at price {} for user {}", quantity, asset.symbol(), assetPrice, userId);

        // remove asset from user portfolio
        Portfolio userPortfolio = portfolioManager.getPortfolioByUser(userId);
        userPortfolio.removeAsset(asset, quantity);
        portfolioManager.savePortfolio(userPortfolio);

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
