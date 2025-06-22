package com.agbafune.tradesys.web;


import com.agbafune.tradesys.api.UserRankAccessor;
import com.agbafune.tradesys.model.Portfolio;
import com.agbafune.tradesys.model.User;
import com.agbafune.tradesys.repository.UserRepository;
import com.agbafune.tradesys.api.PortfolioAccessor;
import com.agbafune.tradesys.api.UserProfileCreator;
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
    private UserProfileCreator userProfileCreator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioAccessor portfolioAccessor;

    @Autowired
    private UserRankAccessor userRankAccessor;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserBean createUser(@RequestBody @Valid UserInputBean input) {
        User model = userProfileCreator.create(input.username());
        return new UserBean(model);
    }

    @GetMapping()
    public List<UserBean> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserBean::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserBean getUserById(@PathVariable Long userId) {
        User user = userRepository.getById(userId);
        return new UserBean(user);
    }

    @GetMapping("/{userId}/stats")
    public UserStatsBean getUserStats(@PathVariable Long userId) {
        User user = userRepository.getById(userId);
        int rank = userRankAccessor.getRank(userId);
        Portfolio portfolio = portfolioAccessor.getPortfolioByUser(userId);

        return UserStatsBean.build(user, portfolio, rank);
    }

    @GetMapping("/leaderboard")
    public List<UserRankBean> getLeaderboard(@RequestParam(required = false, defaultValue = "10") int limit) {
        limit = Math.min(limit, 30); // Limit to a maximum of 30 users
        limit = limit <=0 ? 10 : limit; // Default to 10 if limit is not specified or invalid

        return userRankAccessor.getTopRankedUsers(limit)
                .entrySet()
                .stream()
                .map(entry -> new UserRankBean(
                    userRepository.getById(entry.getKey()),
                    entry.getValue()
                ))
                .toList();
    }
}
