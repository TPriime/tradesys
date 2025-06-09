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

    @PostMapping()
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
}
