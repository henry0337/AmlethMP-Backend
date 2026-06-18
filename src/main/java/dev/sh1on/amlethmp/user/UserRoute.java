package dev.sh1on.amlethmp.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static dev.sh1on.amlethmp.common.shared.route.AmlethMPRoute.BASE_PATH;
import static dev.sh1on.amlethmp.common.shared.route.AmlethMPRoute.USER_ENDPOINT;
import static dev.sh1on.amlethmp.common.shared.route.AmlethMPRoute.USER_ENDPOINT_VERSION;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserRoute {
    public static final String BASE_USER_PATH = BASE_PATH + USER_ENDPOINT + USER_ENDPOINT_VERSION;
}
