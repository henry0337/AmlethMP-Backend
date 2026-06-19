package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.common.shared.service.I18nService;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@RequiredArgsConstructor
@Slf4j
class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;
    private final I18nService i18NService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(
                        i18NService.translateDynamicMessage("error.user.not_found_with_username", new Object[]{username}))))
                .cast(UserDetails.class);
    }
}
