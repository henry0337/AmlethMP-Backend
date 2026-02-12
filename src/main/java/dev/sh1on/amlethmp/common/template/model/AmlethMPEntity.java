package dev.sh1on.amlethmp.common.template.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@Data
public abstract class AmlethMPEntity {
    @Id
    protected String id;

    @Version
    private Integer version;
}
