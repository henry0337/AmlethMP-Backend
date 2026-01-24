package dev.sh1on.amlethmp.common.shared.model;

import java.time.LocalDateTime;

public class Paranoid {
    private boolean isDeleted;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
