package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.sanesoft.hlsserver.database.DatabaseTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AudioFileInfoRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AudioFileInfoRepository audioRepository;

    @Test
    void findByName_returnsAudioFileInfo() {
        // given
        AudioFileInfo audioFileInfo = persistAudioFileInfo("audio", "path", entityManager);

        // when
        Optional<AudioFileInfo> result = audioRepository.findByName(audioFileInfo.getName());

        // then
        assert result.isPresent();
        assertEquals(audioFileInfo, result.get());
    }

    @Test
    void findByNameAndUserName_returnsAudioFileInfo() {
        // given
        AudioFileInfo audioFileInfo = persistAudioFileInfo("audio", "path", "user", entityManager);

        // when
        Optional<AudioFileInfo> result = audioRepository.findByNameAndUserName(audioFileInfo.getName(), "test");

        // then
        assert result.isEmpty();

        // when
        result = audioRepository.findByNameAndUserName(audioFileInfo.getName(), "user");

        // then
        assert result.isPresent();
        assertEquals(audioFileInfo, result.get());
    }
}