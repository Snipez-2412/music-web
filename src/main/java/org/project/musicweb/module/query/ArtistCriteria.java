package org.project.musicweb.module.query;

import lombok.Data;
import org.project.musicweb.common.filter.StringFilter;

@Data
public class ArtistCriteria {
    private StringFilter name;
    private StringFilter country;
}
