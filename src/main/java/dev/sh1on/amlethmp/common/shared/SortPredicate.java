package dev.sh1on.amlethmp.common.shared;

import dev.sh1on.amlethmp.common.shared.constant.SortOrder;

public record SortPredicate(SortOrder direction, String... props) {

}
