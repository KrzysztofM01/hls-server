package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;
import com.sanesoft.hlsserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    ResponseEntity<List<UserResponse>> all() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping()
    ResponseEntity<Void> newUser(@RequestBody UserRequest newUser) {
        userService.addUser(newUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    ResponseEntity<UserResponse> findOne(@PathVariable String name) {
        return ResponseEntity.ok(userService.getUser(name));
    }

    @DeleteMapping("/{name}")
    ResponseEntity<Void> deleteUser(@PathVariable String name) {
        userService.deleteUser(name);
        return ResponseEntity.ok().build();
    }
}
