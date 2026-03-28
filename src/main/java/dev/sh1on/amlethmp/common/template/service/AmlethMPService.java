package dev.sh1on.amlethmp.common.template.service;

import dev.sh1on.amlethmp.common.shared.utils.MessageUtils;
import dev.sh1on.amlethmp.common.shared.utils.ReactorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;

/**
 * <b>[Internal, Service-only]</b> <br>
 * Lớp trừu tượng cơ sở dành cho các lớp {@link org.springframework.stereotype.Service Service} trong hệ thống <b>AmlethMP</b>.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Indexed
public abstract class AmlethMPService {
    protected ReactorUtils reactorUtils;
    protected MessageUtils messageUtils;

    @Autowired
    public void setReactorUtils(ReactorUtils reactorUtils) {
        this.reactorUtils = reactorUtils;
    }

    @Autowired
    public void setMessageUtils(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }
}
