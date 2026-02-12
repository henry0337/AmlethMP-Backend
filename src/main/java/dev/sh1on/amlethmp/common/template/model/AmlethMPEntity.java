package dev.sh1on.amlethmp.common.template.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@Data
public abstract class AmlethMPEntity {
    @Id
    protected String id;

    // TODO: Erm, why you set the visibility of this property is "private"? It should be "protected", right?
    @Version
    private Integer version;
}
