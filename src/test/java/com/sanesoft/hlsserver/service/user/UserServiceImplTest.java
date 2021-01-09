package com.sanesoft.hlsserver.service.user;

import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.exception.EntityNotFoundException;
import com.sanesoft.hlsserver.mapper.DtoMapper;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private DtoMapper<User, UserRequest, UserResponse> userDtoMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private final String name = "test";
    private final User user1 = User.builder().name("test1").build();
    private final User user2 = User.builder().name("test2").build();
    private final UserResponse userResponse1 = new UserResponse("user1", Collections.emptyList());
    private final UserResponse userResponse2 = new UserResponse("user2", Collections.emptyList());
    private final UserRequest userRequest1 = new UserRequest("test1");

    @Test
    void getUsers_returnsUsersFromRepository() {
        // given
        when(repository.findAll())
                .thenReturn(List.of(user1, user2));
        when(userDtoMapper.mapToResponse(user1))
                .thenReturn(userResponse1);
        when(userDtoMapper.mapToResponse(user2))
                .thenReturn(userResponse2);

        // when
        var result = userService.getUsers();

        // then
        assertEquals(List.of(userResponse1, userResponse2), result);
    }

    @Test
    void getUser_returnsUserFromRepository() {
        // given
        when(repository.findByName(name))
                .thenReturn(Optional.of(user1));
        when(userDtoMapper.mapToResponse(user1))
                .thenReturn(userResponse1);

        // when
        var result = userService.getUser(name);

        // then
        assertEquals(userResponse1, result);
    }

    @Test
    void getNonExistingUser_throwsEntityNotFoundException() {
        // given
        when(repository.findByName(name))
                .thenReturn(Optional.empty());

        // when
        var result = assertThrows(
                EntityNotFoundException.class,
                () -> userService.getUser(name)
        );

        // then
        assertNotNull(result);
    }

    @Test
    void addNewUser_mapsWithMapperAndCallsRepository() {
        // given
        when(userDtoMapper.mapFromRequest(userRequest1))
                .thenReturn(user1);

        // when
        userService.addUser(userRequest1);

        // then
        verify(repository, times(1)).save(user1);
    }

    @Test
    void deleteUser_callsRepositoryToDeleteByName() {
        // when
        userService.deleteUser(name);

        // then
        verify(repository, times(1)).deleteByName(name);
    }
}