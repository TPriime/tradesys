package com.agbafune.tradesys.web;


import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.service.PortfolioService;
import com.agbafune.tradesys.domain.service.system.Ranker;
import com.agbafune.tradesys.domain.service.UserService;
import com.agbafune.tradesys.web.model.UserBean;
import com.agbafune.tradesys.web.model.UserInputBean;
import com.agbafune.tradesys.web.model.UserRankBean;
import com.agbafune.tradesys.web.model.UserStatsBean;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        path = "/api/users",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private Ranker ranker;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserBean createUser(@RequestBody @Valid UserInputBean input) {
        User model = userService.create(input.username());
        return new UserBean(model);
    }

    @GetMapping()
    public List<UserBean> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserBean::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserBean getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new UserBean(user);
    }

    @GetMapping("/{userId}/stats")
    public UserStatsBean getUserStats(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        int rank = ranker.getRank(userId);
        Portfolio portfolio = portfolioService.getPortfolioByUser(userId);

        return UserStatsBean.build(user, portfolio, rank);
    }

    @GetMapping("/leaderboard")
    public List<UserRankBean> getLeaderboard(@RequestParam(required = false, defaultValue = "10") int limit) {
        limit = Math.min(limit, 30); // Limit to a maximum of 30 users
        limit = limit <=0 ? 10 : limit; // Default to 10 if limit is not specified or invalid

        return ranker.getTopRankedUsers(limit)
                .entrySet()
                .stream()
                .map(entry -> new UserRankBean(
                    userService.getUserById(entry.getKey()),
                    entry.getValue()
                ))
                .toList();
    }
}
