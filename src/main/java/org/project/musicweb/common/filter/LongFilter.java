package org.project.musicweb.common.filter;

import lombok.Data;

@Data
public class LongFilter {
    private Long equals;
    private Long greaterThan;
    private Long lessThan;
}

