package org.project.musicweb.service;

import org.project.musicweb.entity.ArtistEntity;
import org.project.musicweb.entity.AlbumEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.repository.ArtistRepository;
import org.project.musicweb.repository.AlbumRepository;
import org.project.musicweb.repository.SongRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public AdminService(ArtistRepository artistRepository, AlbumRepository albumRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }


}

