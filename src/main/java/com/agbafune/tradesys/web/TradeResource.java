package com.agbafune.tradesys.web;

import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.service.UserService;
import com.agbafune.tradesys.web.model.TradeInputBean;
import com.agbafune.tradesys.web.model.UserBean;
import com.agbafune.tradesys.web.model.UserInputBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/api/trades",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class TradeResource {

    @Autowired
    private UserService userService;

    @PostMapping("/buy")
    public void trade(TradeInputBean input) {
        //User model = userService.create(input.username());
        //return new UserBean(model);
    }

    @GetMapping()
    public List<UserBean> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserBean::new)
                .collect(Collectors.toList());
    }
}
