package org.project.musicweb.module.query;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.project.musicweb.common.filter.StringFilter;

@Data
@Getter
@Setter
public class ArtistCriteria {
    private StringFilter name;
    private StringFilter country;
}
