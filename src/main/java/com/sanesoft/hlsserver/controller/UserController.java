package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.exception.EntityNotFoundException;
import com.sanesoft.hlsserver.mapper.DtoMapper;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final DtoMapper<User, UserRequest, UserResponse> userDtoMapper;

    @GetMapping()
    ResponseEntity<List<UserResponse>> all() {
        return ResponseEntity.ok(repository.findAll()
                .stream()
                .map(userDtoMapper::mapResponse)
                .collect(Collectors.toList()));
    }

    @PostMapping()
    ResponseEntity<Void> newUser(@RequestBody UserRequest newUser) {
        repository.save(userDtoMapper.mapRequest(newUser));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    ResponseEntity<UserResponse> findOne(@PathVariable String name) {
        return repository.findByName(name)
                .map(userDtoMapper::mapResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(name));
    }

    @DeleteMapping("/{name}")
    ResponseEntity<Void> deleteUser(@PathVariable String name) {
        repository.deleteByName(name);
        return ResponseEntity.ok().build();
    }
}
