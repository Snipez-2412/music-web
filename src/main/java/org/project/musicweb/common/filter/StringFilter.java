package org.project.musicweb.common.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringFilter {

    private String equals;

    private String notEquals;

    private String contains;

    private String notContains;
}