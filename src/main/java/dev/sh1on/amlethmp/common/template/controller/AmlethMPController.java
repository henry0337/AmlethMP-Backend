package dev.sh1on.amlethmp.common.template.controller;

import dev.sh1on.amlethmp.common.shared.utils.ControllerUtils;
import dev.sh1on.amlethmp.common.shared.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;

/**
 * <b>[Internal, Controller-only]</b> <br>
 * Lớp trừu tượng cơ sở (base class) dành cho mọi <b>Controller</b> trong hệ thống <b>AmlethMP</b>.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Indexed
@SuppressWarnings("java:S1694")
public abstract class AmlethMPController {
    protected ControllerUtils controllerUtils;
    protected I18nService i18NService;

    @Autowired(required = false)
    public void setControllerUtils(ControllerUtils controllerUtils) {
        this.controllerUtils = controllerUtils;
    }

    @Autowired(required = false)
    public void setMessageUtils(I18nService i18NService) {
        this.i18NService = i18NService;
    }
}
