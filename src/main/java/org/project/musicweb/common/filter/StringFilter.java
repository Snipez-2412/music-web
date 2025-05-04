package org.project.musicweb.common.filter;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StringFilter {

    private String equals;

    private String notEquals;

    private String contains;

    private String notContains;
}