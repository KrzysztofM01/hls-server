package com.sanesoft.hlsserver.config.typesafe;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Loads {@link Config} from resources and saves it as a spring bean.
 *
 * @author kmirocha
 */
@Configuration
public class TypesafeConfiguration {

    @Bean
    Config engine() {
        return ConfigFactory.load();
    }
}
