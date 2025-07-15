package com.agbafune.tradesys.handler;

import com.agbafune.tradesys.api.SystemTrader;
import com.agbafune.tradesys.handler.model.TradeDataBean;
import com.agbafune.tradesys.model.TradeAction;
import com.agbafune.tradesys.model.TradeData;
import com.agbafune.tradesys.repository.TradeRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.math.BigDecimal;
import java.util.List;

public class TradeHandler {
    private final SystemTrader systemTrader;
    private final TradeRepository tradeRepository;

    public TradeHandler(SystemTrader systemTrader, TradeRepository tradeRepository) {
        this.systemTrader = systemTrader;
        this.tradeRepository = tradeRepository;
    }

    public void configureRoutes(Router router) {
        router.post("/trades/buy").handler(this::buy);
        router.post("/trades/sell").handler(this::sell);
        router.get("/trades").handler(this::getAllTrades);
    }

    private void getAllTrades(RoutingContext ctx) {
        String userId = ctx.queryParams().get("userId");
        List<TradeData> trades = userId != null
                ? tradeRepository.findByUserId(Long.parseLong(userId))
                : tradeRepository.findAll();

        JsonArray jsonArray = new JsonArray();
        trades.stream().map(JsonObject::mapFrom).forEach(jsonArray::add);
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(jsonArray.encode());
    }

    private void buy(RoutingContext ctx) {
        JsonObject reqJson = ctx.body().asJsonObject();
        Long userId = reqJson.getLong("userId");
        Long assetId = reqJson.getLong("assetId");
        BigDecimal quantity = new BigDecimal(reqJson.getString("quantity")) ;

        TradeData trade = systemTrader.submitTrade(userId, assetId, quantity, TradeAction.BUY);
        TradeDataBean model = new TradeDataBean(trade);

        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .end(JsonObject.mapFrom(model).encode());
    }

    private void sell(RoutingContext ctx) {
        JsonObject reqJson = ctx.body().asJsonObject();
        Long userId = reqJson.getLong("userId");
        Long assetId = reqJson.getLong("assetId");
        BigDecimal quantity = new BigDecimal(reqJson.getString("quantity")) ;

        TradeData trade = systemTrader.submitTrade(userId, assetId, quantity, TradeAction.SELL);
        TradeDataBean model = new TradeDataBean(trade);

        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(JsonObject.mapFrom(model).encode());
    }
}
