package dev.sh1on.amlethmp.common.shared.route;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AmlethMPRoute {
    public static final String BASE_PATH = "/api";

    public static final String USER_ENDPOINT_VERSION = "/v1";
    public static final String USER_ENDPOINT = "/user";

    public static final String AUTH_ENDPOINT_VERSION = "/v1";
    public static final String AUTH_ENDPOINT = "/auth";
}
