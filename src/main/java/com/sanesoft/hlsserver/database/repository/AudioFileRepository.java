package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AudioFileRepository extends JpaRepository<AudioFileInfo, Long> {

    List<AudioFileInfo> findByUserName(String name);
}
