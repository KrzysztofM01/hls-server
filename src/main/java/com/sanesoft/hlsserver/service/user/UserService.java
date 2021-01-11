package com.sanesoft.hlsserver.service.user;

import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;

import java.util.List;

/**
 * Service responsible for handling all requests concerning users..
 *
 * @author kmirocha
 */
public interface UserService {

    /**
     * @return list of users.
     */
    List<UserResponse> getUsers();

    /**
     * Searches for user by name.
     *
     * @param name - name of the user
     * @return user.
     */
    UserResponse getUser(String name);

    /**
     * Creates new user from request.
     *
     * @param userRequest - request to add user
     */
    void addUser(UserRequest userRequest);

    /**
     * Deletes user with given name.
     *
     * @param userName - name of the user to delete
     */
    void deleteUser(String userName);
}
