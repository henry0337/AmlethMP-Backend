package dev.sh1on.amlethmp.common.config.production;

import dev.sh1on.amlethmp.common.annotation.EnableReactiveWebCustomization;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Profile("prod")
@EnableReactiveWebCustomization
public class WebProductionConfig implements WebFluxConfigurer { }
