package com.sanesoft.hlsserver.service.user;

import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.exception.EntityNotFoundException;
import com.sanesoft.hlsserver.mapper.DtoMapper;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} which maps database entities using {@link #userDtoMapper} and stores/retrieves
 * information from database via {@link UserRepository}.
 *
 * @author kmirocha
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final DtoMapper<User, UserRequest, UserResponse> userDtoMapper;

    @Override
    public List<UserResponse> getUsers() {
        return repository.findAll()
                .stream()
                .map(userDtoMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(String name) {
        return repository.findByName(name)
                .map(userDtoMapper::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException(name));
    }

    @Override
    public void addUser(UserRequest userRequest) {
        repository.save(userDtoMapper.mapFromRequest(userRequest));
    }

    @Override
    public void deleteUser(String userName) {
        repository.deleteByName(userName);
    }
}
