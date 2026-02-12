package dev.sh1on.amlethmp.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static dev.sh1on.amlethmp.common.shared.route.AmlethMPRoute.*;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserRoute {
    public static final String BASE_USER_PATH = BASE_PATH + USER_ENDPOINT + USER_ENDPOINT_VERSION;
}
