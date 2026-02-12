package dev.sh1on.amlethmp.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageUtils {
    private final MessageSource messageSource;

    public String obtainLocalizedStaticMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}
