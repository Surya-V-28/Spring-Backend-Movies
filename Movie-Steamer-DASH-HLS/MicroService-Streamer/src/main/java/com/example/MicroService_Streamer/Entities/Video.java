package com.example.MicroService_Streamer.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Video {

    @Id
    private String name;
    private String description;
    private String videoPath;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}

