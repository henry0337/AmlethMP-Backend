package dev.sh1on.amlethmp.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static dev.sh1on.amlethmp.common.shared.route.AmlethMPRoute.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthRoute {
    public static final String BASE_AUTH_PATH = BASE_PATH + AUTH_ENDPOINT + AUTH_ENDPOINT_VERSION;
}
