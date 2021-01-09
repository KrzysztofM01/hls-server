package com.sanesoft.hlsserver.database.entity;

import com.sanesoft.hlsserver.database.converter.PathConverter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = AudioFileInfo.TABLE_NAME)
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = {"user", "createdOn", "id"})
public class AudioFileInfo {

    public static final String TABLE_NAME = "audio_file_info";

    @Id
    @GeneratedValue
    Long id;
    @CreationTimestamp
    Instant createdOn;
    String name;
    @Column(name = "path_to_file")
    @Convert(converter = PathConverter.class)
    Path pathToFile;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    User user;
}