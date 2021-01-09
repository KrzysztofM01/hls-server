package com.sanesoft.hlsserver.service.user;

import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserResponse getUser(String name);

    void addUser(UserRequest userRequest);

    void deleteUser(String userName);
}
