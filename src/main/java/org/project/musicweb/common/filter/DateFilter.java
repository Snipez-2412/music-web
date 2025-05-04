package org.project.musicweb.common.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class DateFilter {
    private LocalDate equals;
    private LocalDate before;
    private LocalDate after;
}
