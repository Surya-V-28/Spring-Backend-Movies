package com.example.MicroService_Streamer.Service;

import com.example.MicroService_Streamer.Entities.Video;
import com.example.MicroService_Streamer.Repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void uploadVideo(MultipartFile file, String name, String description) throws IOException {
        // Save the file
        String videoFolder = Paths.get(System.getProperty("user.dir"), "videos").toString();

        // Ensure the folder exists
        File folder = new File(videoFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Define the full video file path
        String videoPath = videoFolder + "/" + name + ".mp4";
        File videoFile = new File(videoPath);

        // Save the uploaded file to the path
        file.transferTo(videoFile);

        // Save metadata to database
        Video video = new Video();
        video.setName(name);
        video.setDescription(description);
        video.setVideoPath(videoPath);
        videoRepository.save(video);

        // Start video processing in a separate thread
        new Thread(() -> processVideo(video)).start();
    }

    // Video processing method (DASH conversion)
    private void processVideo(Video video) {
        // Convert video to DASH format (FFmpeg)
        String videoPath = video.getVideoPath();
        String mpdFile = "videos/" + video.getName() + ".mpd";

        try {
            // FFmpeg conversion (you should have FFmpeg installed)
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-i", videoPath, "-preset", "ultrafast", "-g", "48", "-sc_threshold", "0",
                    "-b:v", "1M", "-b:a", "128k", "-adaptation_sets", "id=0,streams=v id=1,streams=a",
                    "-f", "dash", mpdFile);
            pb.start().waitFor(); // Wait until conversion is done
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

