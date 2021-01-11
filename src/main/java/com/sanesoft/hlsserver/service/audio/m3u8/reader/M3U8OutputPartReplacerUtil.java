package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class which helps with reading encoded m3u8 metadata file,
 * see {@link #replaceOutputPart(String, URI, String, String)} for more information about how this process is done.
 *
 * @author kmirocha
 */
@UtilityClass
public class M3U8OutputPartReplacerUtil {

    private final Pattern PART_LOCATION_PATTERN = Pattern.compile("^output([0-9]*)\\.ts$");

    /**
     * Changes given audio chunk information into location where this audio chunk can be requested. For example, a line:
     * <code>output23.ts</code> can be changed into
     * <code>http://serverAddress:port/users/user-name/audio-files/audio-name/23</code>.
     *
     * @param inputLine - line from the m3u8 metadata file
     * @param baseUri - base uri of server where all audio file are served
     * @param userName - name of the user
     * @param audioName - name of the audio
     * @return uri with location where given audio chunk can be accessed from.
     */
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
