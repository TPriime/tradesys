package com.agbafune.tradesys.web;

import com.agbafune.tradesys.domain.model.*;
import com.agbafune.tradesys.domain.service.*;
import com.agbafune.tradesys.web.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

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

    @GetMapping()
    public List<UserBean> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserBean::new)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserInputBean input) {
        if(userService.findUserByUsername(input.username()).isPresent()) {
            return new ResponseEntity<>(Map.of("error", "Username already exists"), HttpStatus.BAD_REQUEST);
        }

        User model = userService.create(input.username());
        return ResponseEntity.of(Optional.of(new UserBean(model)));
    }

    @GetMapping("/{userId}")
    public UserBean getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new UserBean(user);
    }



    @GetMapping("/{userId}/stats")
    public UserStatsBean getUserStats(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Portfolio portfolio = portfolioService.getPortfolioByUser(user.id());
        return UserStatsBean.build(user, portfolio);
    }

    @GetMapping("/{userId}/leaderboard")
    public UserStatsBean getLeaderboard(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Portfolio portfolio = portfolioService.getPortfolioByUser(user.id());
        return UserStatsBean.build(user, portfolio);
    }
}
