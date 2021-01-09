package com.sanesoft.hlsserver.database;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.nio.file.Path;

@UtilityClass
public class DatabaseTestUtil {

    public User persistTestUser(String name, TestEntityManager entityManager) {
        return entityManager.persistAndFlush(User.builder()
                .name(name)
                .build());
    }

    public User persistTestUser(TestEntityManager entityManager) {
        return persistTestUser("test", entityManager);
    }

    public AudioFileInfo persistAudioFileInfo(String audioName,
                                              String audioPath,
                                              String userName,
                                              TestEntityManager entityManager) {
        return entityManager.persistAndFlush(AudioFileInfo.builder()
                .name(audioName)
                .pathToFile(Path.of(audioPath))
                .user(persistTestUser(userName, entityManager))
                .build());
    }

    public AudioFileInfo persistAudioFileInfo(String audioName, String audioPath, TestEntityManager entityManager) {
        return persistAudioFileInfo(audioName, audioPath, "test", entityManager);
    }
}
