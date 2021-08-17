package com.example.music.backend.video.repository;

import com.example.music.backend.video.domain.YoutubeVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideo, Long> {

    Optional<YoutubeVideo> findByYoutubeId(String youtubeId);
}
