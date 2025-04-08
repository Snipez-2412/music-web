package org.project.musicweb.service;

import org.project.musicweb.entity.LikeEntity;
import org.project.musicweb.entity.SongEntity;
import org.project.musicweb.entity.UserEntity;
import org.project.musicweb.repository.LikeRepository;
import org.project.musicweb.repository.SongRepository;
import org.project.musicweb.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, SongRepository songRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public LikeEntity likeSong(Long userId, Long songId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        Optional<LikeEntity> existingLike = likeRepository.findByUserAndSong(user, song);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("Song already liked");
        }

        LikeEntity like = new LikeEntity();
        like.setUser(user);
        like.setSong(song);
        return likeRepository.save(like);
    }

    public void unlikeSong(Long userId, Long songId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        LikeEntity like = likeRepository.findByUserAndSong(user, song)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));

        likeRepository.delete(like);
    }

    public List<SongEntity> getLikedSongsByUser(Long userId) {
        List<LikeEntity> likes = likeRepository.findByUser_UserID(userId);
        return likes.stream()
                .map(LikeEntity::getSong)
                .toList();
    }
}
