package com.agbafune.tradesys;

import com.agbafune.tradesys.config.Factory;
import com.agbafune.tradesys.handler.FailureHandler;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ApiServerVerticle extends VerticleBase {

    private final int port;

    ApiServerVerticle(int port) {
        this.port = port;
    }

    @Override
    public Future<?> start() {
        Router router = Router.router(vertx);
        Router sub = Router.router(vertx);

        Factory.getUserHandler().configureRoutes(sub);
        Factory.getAssetHandler().configureRoutes(sub);
        Factory.getTradeHandler().configureRoutes(sub);

        router.route("/api/*")
                .handler(BodyHandler.create())
                .failureHandler(FailureHandler::handleFailure)
                .subRouter(sub);

        return vertx.createHttpServer().requestHandler(router).listen(port).onSuccess(http -> {
            System.out.println("HTTP server started on port " + port);
        });
    }

}
