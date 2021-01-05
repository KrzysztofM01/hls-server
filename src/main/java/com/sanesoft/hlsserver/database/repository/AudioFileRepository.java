package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AudioFileRepository extends JpaRepository<AudioFileInfo, Long> {

    @Query("select afi from AudioFileInfo afi " +
            "join User u on u=afi.user " +
            "where afi.name = :name " +
            "and u.name = :userName")
    Optional<AudioFileInfo> findByNameAndUserName(String name, String userName);

    Optional<AudioFileInfo> findByName(String name);


}
