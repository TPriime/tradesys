package com.agbafune.tradesys.handler;

import com.agbafune.tradesys.api.PortfolioAccessor;
import com.agbafune.tradesys.api.UserProfileCreator;
import com.agbafune.tradesys.api.UserRankAccessor;
import com.agbafune.tradesys.handler.model.UserRankBean;
import com.agbafune.tradesys.handler.model.UserStatsBean;
import com.agbafune.tradesys.model.Portfolio;
import com.agbafune.tradesys.model.User;
import com.agbafune.tradesys.repository.UserRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class UserHandler {
    private final UserProfileCreator userProfileCreator;
    private final UserRepository userRepository;
    private final PortfolioAccessor portfolioAccessor;
    private final UserRankAccessor userRankAccessor;

    public UserHandler(
            UserProfileCreator userProfileCreator,
            UserRepository userRepository,
            PortfolioAccessor portfolioAccessor,
            UserRankAccessor userRankAccessor
    ) {
        this.userProfileCreator = userProfileCreator;
        this.userRepository = userRepository;
        this.portfolioAccessor = portfolioAccessor;
        this.userRankAccessor = userRankAccessor;
    }


    public void configureRoutes(Router router) {
        router.post("/users").handler(this::createUser);
        router.get("/users").handler(this::getAllUsers);
        router.get("/users/leaderboard").handler(this::getLeaderboard);
        router.get("/users/:userId").handler(this::getUser);
        router.get("/users/:userId/stats").handler(this::getUserStats);
    }

    private void createUser(RoutingContext ctx) {
        JsonObject reqJson = ctx.body().asJsonObject();
        String username = reqJson.getString("username");
        User model = userProfileCreator.create(username);
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .end(JsonObject.mapFrom(model).encode());
    }

    private void getAllUsers(RoutingContext ctx) {
        JsonArray jsonArray = new JsonArray();
        userRepository.findAll().stream()
                .map(JsonObject::mapFrom).forEach(jsonArray::add);

        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(jsonArray.encode());
    }

    private void getUser(RoutingContext ctx) {
        Long userId = Long.parseLong(ctx.pathParam("userId"));
        User user = userRepository.getById(userId);
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(JsonObject.mapFrom(user).encode());
    }

    private void getUserStats(RoutingContext ctx) {
        Long userId = Long.valueOf(ctx.pathParam("userId"));

        User user = userRepository.getById(userId);
        int rank = userRankAccessor.getRank(userId);
        Portfolio portfolio = portfolioAccessor.getPortfolioByUser(userId);

        UserStatsBean res = UserStatsBean.build(user, portfolio, rank);
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(JsonObject.mapFrom(res).encode());
    }

    private void getLeaderboard(RoutingContext ctx) {
        String limitStr = ctx.queryParams().get("limit");
        int limit = limitStr != null ? Integer.parseInt(limitStr) : 0;
        limit = Math.min(limit, 30); // Limit to a maximum of 30 users
        limit = limit <= 0 ? 10 : limit; // Default to 10 if limit is not specified or invalid

        JsonArray jsonArray = new JsonArray();
        userRankAccessor.getTopRankedUsers(limit)
                .entrySet()
                .stream()
                .map(entry -> new UserRankBean(
                        userRepository.getById(entry.getKey()),
                        entry.getValue()
                ))
                .forEach(jsonArray::add);

        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(jsonArray.encode());
    }
}
