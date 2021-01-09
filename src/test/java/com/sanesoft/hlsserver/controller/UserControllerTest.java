package com.sanesoft.hlsserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.AudioFileInfoResponse;
import com.sanesoft.hlsserver.model.response.UserResponse;
import com.sanesoft.hlsserver.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private final String userName = "user1";
    private final UserResponse userResponse1 = new UserResponse(userName, Collections.emptyList());
    private final UserResponse userResponse2 = new UserResponse("user2", Collections.emptyList());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getUsers_returnsJsonArray() throws Exception {
        // given
        Mockito.when(service.getUsers())
                .thenReturn(List.of(userResponse1, userResponse2));

        // expect
        mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(userResponse1.getName())))
                .andExpect(jsonPath("$[1].name", is(userResponse2.getName())));
    }

    @Test
    void getUserNyName_returnsUserJson() throws Exception {
        // given
        Mockito.when(service.getUser(userName))
                .thenReturn(userResponse1);

        // expect
        mvc.perform(get("/users/" + userName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userName)))
                .andExpect(jsonPath("$.audioFileInfos", emptyCollectionOf(AudioFileInfoResponse.class)));
    }

    @Test
    void addUser_callsServiceToAddNewUser() throws Exception {
        // given
        UserRequest userRequest = UserRequest.builder()
                .name(userName)
                .build();

        // when
        var result = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // then
        Assertions.assertEquals("", result.getResponse().getContentAsString());
        Mockito.verify(service, Mockito.times(1)).addUser(userRequest);
    }

    @Test
    void deleteUser_callsServiceToDeleteUser() throws Exception {
        // when
        var result = mvc.perform(delete("/users/" + userName))
                .andExpect(status().isOk())
                .andReturn();

        // then
        Assertions.assertEquals("", result.getResponse().getContentAsString());
        Mockito.verify(service, Mockito.times(1)).deleteUser(userName);
    }
}