package org.project.musicweb.module.query;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.project.musicweb.common.filter.LongFilter;
import org.project.musicweb.common.filter.StringFilter;

@Data
@Getter
@Setter
public class PlaylistSongCriteria {
    private StringFilter songTitle;
    private StringFilter albumTitle;
    private StringFilter artistName;
    private LongFilter playlistId;
}
