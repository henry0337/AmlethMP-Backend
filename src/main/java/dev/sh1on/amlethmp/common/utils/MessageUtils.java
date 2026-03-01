package dev.sh1on.amlethmp.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageUtils {
    private final MessageSource messageSource;

    public String obtainStaticLocalizedMessage(String code) {
        try {
            return messageSource.getMessage(code, null, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            log.warn(e.getLocalizedMessage());
            return "";
        }
    }

    public String obtainLocalizedMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
}
