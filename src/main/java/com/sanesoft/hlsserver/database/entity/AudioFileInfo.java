package com.sanesoft.hlsserver.database.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = AudioFileInfo.TABLE_NAME)
@ToString(exclude = "user")
public class AudioFileInfo {

    public static final String TABLE_NAME = "audio_file_info";

    @Id
    @GeneratedValue
    Long id;
    @CreationTimestamp
    Instant createdOn;
    String name;
    @Column(name = "path_to_file")
    String pathToFile;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    User user;
}