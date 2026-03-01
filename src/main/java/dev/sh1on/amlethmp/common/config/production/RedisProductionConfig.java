package dev.sh1on.amlethmp.common.config.production;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class RedisProductionConfig { }
