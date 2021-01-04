package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.exception.EntityNotFoundException;
import com.sanesoft.hlsserver.mapper.DtoMapper;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final DtoMapper<User, UserRequest, UserResponse> userDtoMapper;

    // TODO add ResponseEntity wrappers as return type
    @GetMapping("/users")
    List<UserResponse> all() {
        return repository.findAll()
                .stream()
                .map(userDtoMapper::mapResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    void newUser(@RequestBody UserRequest newUser) {
        repository.save(userDtoMapper.mapRequest(newUser));
    }

    @GetMapping("/users/{name}")
    UserResponse findOne(@PathVariable String name) {
        return repository.findByName(name)
                .map(userDtoMapper::mapResponse)
                .orElseThrow(() -> new EntityNotFoundException(name));
    }

    @DeleteMapping("/users/{name}")
    void deleteUser(@PathVariable String name) {
        repository.deleteByName(name);
    }
}
