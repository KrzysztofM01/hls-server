package com.sanesoft.hlsserver.database.converter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathConverterTest {

    private final PathConverter pathConverter = new PathConverter();
    private final String string = "test";
    private final Path path = Path.of(string);

    @Test
    void convertToDatabaseColumn_returnsString() {
        // when
        String result = pathConverter.convertToDatabaseColumn(path);

        // then
        assertEquals(string, result);
    }

    @Test
    void convertToEntityAttribute_returnsPath() {
        // when
        Path result = pathConverter.convertToEntityAttribute(string);

        // then
        assertEquals(path, result);
    }
}