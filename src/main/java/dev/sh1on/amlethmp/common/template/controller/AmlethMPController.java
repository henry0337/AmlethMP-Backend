package dev.sh1on.amlethmp.common.template.controller;

import dev.sh1on.amlethmp.common.shared.utils.ControllerUtils;
import dev.sh1on.amlethmp.common.shared.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;

/**
 * <b>[Internal, Controller-only]</b> <br>
 * Lớp trừu tượng cơ sở (base class) dành cho mọi <b>Controller</b> trong hệ thống <b>AmlethMP</b>.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Indexed
public abstract class AmlethMPController {
    protected ControllerUtils controllerUtils;
    protected MessageUtils messageUtils;

    @Autowired
    public void setControllerUtils(ControllerUtils controllerUtils) {
        this.controllerUtils = controllerUtils;
    }

    @Autowired
    public void setMessageUtils(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }
}
