package org.project.musicweb.common.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateFilter {
    private LocalDate equals;
    private LocalDate before;
    private LocalDate after;
}
