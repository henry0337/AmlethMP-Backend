package dev.sh1on.amlethmp.common.template.service;

import dev.sh1on.amlethmp.common.shared.service.I18nService;
import dev.sh1on.amlethmp.common.shared.utils.ReactorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;

/**
 * <b>[Internal API, Service-only]</b> <br>
 * Lớp trừu tượng cơ sở dành cho các lớp {@link org.springframework.stereotype.Service Service} trong hệ thống <b>AmlethMP</b>.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Indexed
@SuppressWarnings("java:S1694")
public abstract class AmlethMPService {
    protected ReactorUtils reactorUtils;
    protected I18nService i18NService;

    @Autowired(required = false)
    public void setReactorUtils(ReactorUtils reactorUtils) {
        this.reactorUtils = reactorUtils;
    }

    @Autowired(required = false)
    public void setMessageUtils(I18nService i18NService) {
        this.i18NService = i18NService;
    }
}
