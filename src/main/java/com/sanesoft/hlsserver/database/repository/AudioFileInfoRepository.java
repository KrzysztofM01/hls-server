package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * JpaRepository responsible for persisting and retrieving {@link AudioFileInfo} data from database.
 *
 * @author kmirocha
 */
public interface AudioFileInfoRepository extends JpaRepository<AudioFileInfo, Long> {

    /**
     * Searches for {@link AudioFileInfo} with given name and parent user name.
     *
     * @param name - name of the audio file
     * @param userName - name of the parent user
     * @return optional with possible {@link AudioFileInfo} if it was found.
     */
    @Query("select afi from AudioFileInfo afi " +
            "join User u on u=afi.user " +
            "where afi.name = :name " +
            "and u.name = :userName")
    Optional<AudioFileInfo> findByNameAndUserName(String name, String userName);

    /**
     * Searches for {@link AudioFileInfo} with given name.
     *
     * @param name - name of the audio file
     * @return optional with possible {@link AudioFileInfo} if it was found.
     */
    Optional<AudioFileInfo> findByName(String name);


}
