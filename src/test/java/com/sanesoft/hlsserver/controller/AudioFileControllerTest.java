package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.service.audio.AudioFileService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AudioFileController.class)
class AudioFileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AudioFileService service;

    @Captor
    ArgumentCaptor<InputStream> captor;

    private final String userName = "some-user";
    private final String audioName = "some-audio";
    private final byte[] bytes = {1, 2, 3};
    private final InputStream inputStream = new ByteArrayInputStream(bytes);

    @Test
    void addAudioFile_callsServiceToAddNewAudioFile() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "test_audio.mp3", "audio/mpeg", inputStream);

        // expect
        mvc.perform(multipart("/users/" + userName + "/audio-files/" + audioName)
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(service).saveAudioFile(eq(userName), eq(audioName), captor.capture());
        assertArrayEquals(bytes, captor.getValue().readAllBytes());
    }

    @Test
    void getAudioFileIndex_callsServiceToReturnIndex() throws Exception {
        // given
        String someM3U8Index = "something";
        when(service.getAudioFileIndex(userName, audioName))
                .thenReturn(someM3U8Index);

        // expect
        mvc.perform(get("/users/" + userName + "/audio-files/index/" + audioName)
                .contentType("audio/mpegurl"))
                .andExpect(status().isOk())
                .andExpect(content().string(someM3U8Index));
    }

    @Test
    void getAudioFile_callsServiceToReturnFile() throws Exception {
        // given
        when(service.getAudioFile(userName, audioName, 1))
                .thenReturn(bytes);

        // expect
        mvc.perform(get("/users/" + userName + "/audio-files/" + audioName + "/" + 1)
                .contentType("video/mp2t"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(bytes));
    }
}