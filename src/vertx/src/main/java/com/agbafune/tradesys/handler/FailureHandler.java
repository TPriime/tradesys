package com.agbafune.tradesys.handler;

import com.agbafune.tradesys.exception.ConflictException;
import com.agbafune.tradesys.exception.InsufficientAssetsException;
import com.agbafune.tradesys.exception.InsufficientFundsException;
import com.agbafune.tradesys.exception.ModelNotFoundException;
import com.agbafune.tradesys.exception.PortfolioNotFoundException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class FailureHandler {

    public static void handleFailure(RoutingContext ctx) {
        Throwable t = ctx.failure();
        int status = switch (t) {
            case ModelNotFoundException _ -> 404;
            case InsufficientFundsException _ -> 403;
            case PortfolioNotFoundException _ -> 404;
            case InsufficientAssetsException _ -> 403;
            case ConflictException _ -> 409;
            default -> 500;
        };

        JsonObject msg = new JsonObject();
        msg.put("message", t.getMessage());

        ctx.response().setStatusCode(status).putHeader("Content-Type", "application/json")
                .end(msg.encode());
    }
}
