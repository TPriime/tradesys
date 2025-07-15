package com.agbafune.tradesys;

import com.agbafune.tradesys.config.DataInitializer;
import com.agbafune.tradesys.config.Factory;
import com.agbafune.tradesys.config.JacksonConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.ThreadingModel;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;

public class MainVerticle extends VerticleBase {

    private final long simulationDelay = 15000;
    private final int port = 8888;

    @Override
    public Future<?> start() {
        System.setProperty("vertx.logger-delegate-factory-class-name",
                "io.vertx.core.logging.SLF4JLogDelegateFactory");
        JacksonConfig.configure();

        Factory.init(vertx.eventBus());
        DataInitializer.initialize(Factory.getAssetRepository(), Factory.getUserRepository());

        // Periodically simulate asset price changes
        vertx.setPeriodic(simulationDelay, simulationDelay, _ -> {
            Factory.getAssetChangeSimulator().updateAssetPrices();
        });

        DeploymentOptions serverOpts =
                new DeploymentOptions().setThreadingModel(ThreadingModel.VIRTUAL_THREAD);

        return vertx.deployVerticle(() -> new ApiServerVerticle(port), serverOpts);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}

