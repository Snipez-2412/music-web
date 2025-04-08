package org.project.musicweb.module.query;

import lombok.Data;
import org.project.musicweb.common.filter.DateFilter;
import org.project.musicweb.common.filter.StringFilter;

@Data
public class AlbumCriteria {
    private StringFilter title;
    private StringFilter artistName;
    private DateFilter releaseDate;
}

