package org.project.musicweb.module.query;

import lombok.Data;
import org.project.musicweb.common.filter.LongFilter;
import org.project.musicweb.common.filter.StringFilter;

@Data
public class SongCriteria {
    private StringFilter title;
    private StringFilter genre;
    private LongFilter albumId;
}
