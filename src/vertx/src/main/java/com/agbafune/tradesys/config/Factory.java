package com.agbafune.tradesys.config;

import com.agbafune.tradesys.PortfolioManager;
import com.agbafune.tradesys.SystemRewarder;
import com.agbafune.tradesys.SystemTraderImpl;
import com.agbafune.tradesys.UserRankManager;
import com.agbafune.tradesys.UserService;
import com.agbafune.tradesys.api.AssetChangeSimulator;
import com.agbafune.tradesys.api.SystemTrader;
import com.agbafune.tradesys.api.UserProfileCreator;
import com.agbafune.tradesys.api.UserRankAccessor;
import com.agbafune.tradesys.event.AppEventListener;
import com.agbafune.tradesys.event.AppEventListenerImpl;
import com.agbafune.tradesys.event.AppEventPublisher;
import com.agbafune.tradesys.event.AppEventPublisherImpl;
import com.agbafune.tradesys.handler.AssetHandler;
import com.agbafune.tradesys.handler.TradeHandler;
import com.agbafune.tradesys.handler.UserHandler;
import com.agbafune.tradesys.repository.AssetRepository;
import com.agbafune.tradesys.repository.AssetRepositoryImpl;
import com.agbafune.tradesys.repository.PortfolioRepository;
import com.agbafune.tradesys.repository.PortfolioRepositoryImpl;
import com.agbafune.tradesys.repository.TradeRepository;
import com.agbafune.tradesys.repository.TradeRepositoryImpl;
import com.agbafune.tradesys.repository.UserRepository;
import com.agbafune.tradesys.repository.UserRepositoryImpl;
import com.agbafune.tradesys.simulator.AssetSimulator;
import io.vertx.core.eventbus.EventBus;

public class Factory {

    private static UserRepository userRepository;
    private static AssetRepository assetRepository;
    private static AssetChangeSimulator assetChangeSimulator;

    private static UserHandler userHandler;
    private static AssetHandler assetHandler;
    private static TradeHandler tradeHandler;

    public static void init(EventBus eventBus) {
        AppEventListener eventListener = new AppEventListenerImpl(eventBus);
        PortfolioRepository portfolioRepository = new PortfolioRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        UserService userService = new UserService(userRepository);
        assetRepository = new AssetRepositoryImpl();
        PortfolioManager portfolioManager = new PortfolioManager(portfolioRepository, assetRepository);
        UserRankAccessor userRankAccessor = new UserRankManager(eventListener);
        TradeRepository tradeRepository = new TradeRepositoryImpl();
        AppEventPublisher eventPublisher = new AppEventPublisherImpl(eventBus);
        SystemTrader systemTrader = new SystemTraderImpl(
                assetRepository,
                userService,
                portfolioManager,
                tradeRepository,
                eventPublisher);

        assetChangeSimulator = new AssetSimulator(assetRepository);
        assetHandler = new AssetHandler(assetRepository);
        tradeHandler = new TradeHandler(systemTrader, tradeRepository);

        userHandler = new UserHandler(
                userService,
                userRepository,
                portfolioManager,
                userRankAccessor);

        // Most decoupled service
        new SystemRewarder(userService, eventPublisher, eventListener);
    }

    public static AssetRepository getAssetRepository() {
        return assetRepository;
    }


    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static AssetChangeSimulator getAssetChangeSimulator() {
        return assetChangeSimulator;
    }

    public static UserHandler getUserHandler() {
        return userHandler;
    }

    public static AssetHandler getAssetHandler() {
        return assetHandler;
    }

    public static TradeHandler getTradeHandler() {
        return tradeHandler;
    }
}
