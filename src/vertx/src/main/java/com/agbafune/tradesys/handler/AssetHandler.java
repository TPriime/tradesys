package com.agbafune.tradesys.handler;

import com.agbafune.tradesys.handler.model.AssetBean;
import com.agbafune.tradesys.repository.AssetRepository;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class AssetHandler {
    private final AssetRepository assetRepository;

    public AssetHandler(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public void configureRoutes(Router router) {
        router.get("/assets").handler(this::getAllAssets);
    }

    private void getAllAssets(RoutingContext ctx) {
        JsonArray jsonArray = new JsonArray();
        assetRepository.findAll()
                .stream()
                .map(JsonObject::mapFrom)
                .forEach(jsonArray::add);

        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(jsonArray.encode());
    }
}
