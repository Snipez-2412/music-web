package org.project.musicweb.common.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LongFilter {
    private Long equals;
    private Long greaterThan;
    private Long lessThan;
}

