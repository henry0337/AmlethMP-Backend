package dev.sh1on.amlethmp.common.config.production;

import dev.sh1on.amlethmp.common.annotation.EnableReactiveWebCustomization;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@Profile("prod")
@EnableReactiveWebCustomization
public class WebProductionConfig implements WebFluxConfigurer { }
