package com.sanesoft.hlsserver.service.audio;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.database.repository.AudioFileInfoRepository;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.exception.EntityAlreadyExistsException;
import com.sanesoft.hlsserver.exception.EntityNotFoundException;
import com.sanesoft.hlsserver.service.audio.m3u8.encoder.M3U8Encoder;
import com.sanesoft.hlsserver.service.audio.m3u8.reader.M3U8FileReader;
import com.sanesoft.hlsserver.service.audio.reader.AudioFileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioFileServiceImplTest {

    @Mock
    private AudioFileInfoRepository audioRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private M3U8Encoder m3U8Encoder;
    @Mock
    private M3U8FileReader m3U8FileReader;
    @Mock
    private AudioFileReader audioFileReader;
    @Mock
    InputStream inputStream;
    @InjectMocks
    private AudioFileServiceImpl audioService;

    private final String userName = "some-user";
    private final String audioName = "some-audio";
    private final byte[] someBytes = {1, 2, 3};
    private final Path parentPath = Path.of("parent");
    private final Path somePath = parentPath.resolve("some-path");
    private final AudioFileInfo audioFileInfo = AudioFileInfo.builder()
            .name(audioName)
            .pathToFile(somePath)
            .build();
    private final User user = User.builder()
            .name(userName)
            .build();

    @Test
    void saveAudioFile_searchesForUserAndUseInRepositoryAndEncodesFile() {
        // given
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.empty());
        when(userRepository.findByName(userName))
                .thenReturn(Optional.of(user));
        when(m3U8Encoder.encodeFileToM3UFormat(userName, audioName, inputStream))
                .thenReturn(somePath);

        // when
        audioService.saveAudioFile(userName, audioName, inputStream);

        // then
        verify(audioRepository, times(1)).save(AudioFileInfo.builder()
                .user(user)
                .name(audioName)
                .pathToFile(somePath)
                .build());
    }

    @Test
    void saveAudioFileWithAudioExisting_throwsEntityAlreadyExistException() {
        // given
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.of(audioFileInfo));

        // when
        var result = assertThrows(
                EntityAlreadyExistsException.class,
                () -> audioService.saveAudioFile(userName, audioName, inputStream)
        );

        // then
        assertNotNull(result);
    }

    @Test
    void saveAudioFileWithNonExistingUser_throwsEntityNotFoundException() {
        // given
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.empty());
        when(userRepository.findByName(userName))
                .thenReturn(Optional.empty());

        // when
        var result = assertThrows(
                EntityNotFoundException.class,
                () -> audioService.saveAudioFile(userName, audioName, inputStream)
        );

        // then
        assertNotNull(result);
    }

    @Test
    void getAudioFileIndex_returnsPathFromAudioFileInfoFromRepository() {
        // given
        String someString = "some-string";
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.of(audioFileInfo));
        when(m3U8FileReader.readEncodedM3U8File(somePath, userName, audioName))
                .thenReturn(someString);

        // when
        var result = audioService.getAudioFileIndex(userName, audioName);

        // then
        assertEquals(someString, result);
    }

    @Test
    void getAudioFileIndexWithNonExistingAudio_throwsEntityNotFoundException() {
        // given
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.empty());

        // when
        var result = assertThrows(
                EntityNotFoundException.class,
                () -> audioService.getAudioFileIndex(userName, audioName)
        );

        // then
        assertNotNull(result);
    }

    @Test
    void getAudioFile_returnsFileFromAudioFileReader() {
        // given
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.of(audioFileInfo));
        when(audioFileReader.readAudioPartFile(parentPath, 1))
                .thenReturn(someBytes);

        // when
        var result = audioService.getAudioFile(userName, audioName, 1);

        // then
        assertArrayEquals(someBytes, result);
    }

    @Test
    void getAudioFileWithNonExistingAudio_throwsEntityNotFoundException() {
        // given
        when(audioRepository.findByNameAndUserName(audioName, userName))
                .thenReturn(Optional.empty());

        // when
        var result = assertThrows(
                EntityNotFoundException.class,
                () -> audioService.getAudioFile(userName, audioName, 1)
        );

        // then
        assertNotNull(result);
    }
}