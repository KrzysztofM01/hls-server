package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class M3U8OutputPartReplacerUtil {

    private final Pattern PART_LOCATION_PATTERN = Pattern.compile("^output([0-9]*)\\.ts$");

    public String replaceOutputPart(String inputLine,
                                    URI baseUri,
                                    String userName,
                                    String audioName) {
        Matcher matcher = PART_LOCATION_PATTERN.matcher(inputLine);
        if (matcher.find()) {
            return UriComponentsBuilder.newInstance()
                    .uri(baseUri)
                    .pathSegment("users")
                    .pathSegment(userName)
                    .pathSegment("audio-files")
                    .pathSegment(audioName)
                    .pathSegment(matcher.group(1))
                    .build()
                    .toUriString();
        }
        return inputLine;
    }
}
