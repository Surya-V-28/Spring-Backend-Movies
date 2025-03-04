package com.example.MicroService_Streamer.Repository;


import com.example.MicroService_Streamer.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, String> {
}