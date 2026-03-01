package dev.sh1on.amlethmp.common.template.model;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

@Data
public abstract class AmlethMPEntity {
    @Id
    protected String id;

    @Version
    protected Integer version;

    @Column("created_at")
    @CreatedDate
    @InsertOnlyProperty
    protected String createdAt;

    @Column("created_by")
    @CreatedBy
    @InsertOnlyProperty
    protected String createdBy;

    @Column("last_updated_at")
    @LastModifiedDate
    protected String lastUpdatedAt;

    @Column("last_updated_by")
    @LastModifiedBy
    protected String lastUpdatedBy;
}
