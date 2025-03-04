package com.example.MicroService_Streamer.Controller;

import com.example.MicroService_Streamer.Service.VideoService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;

@CrossOrigin(origins = "http://localhost:5500")
@Controller
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("name") String name,
                                   @RequestParam("description") String description) {
        try {
            videoService.uploadVideo(file, name, description);
            return "upload-success"; // Return a success view
        } catch (Exception e) {
            e.printStackTrace();
            return "upload-error"; // Return error view
        }
    }

    // Serve the DASH manifest file
    @GetMapping("/{videoName}.mpd")
    @ResponseBody
    public ResponseEntity<Resource> serveMpd(@PathVariable String videoName) {
        // Locate the video
        File mpdFile = new File("videos/" + videoName + ".mpd");
        Resource resource = new FileSystemResource(mpdFile);
        return ResponseEntity.ok(resource);
    }

    // Serve the video segments (just like before)
    @GetMapping("/segments/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveVideoSegment(@PathVariable String filename) {
        File segmentFile = new File("videos/" + filename);
        Resource resource = new FileSystemResource(segmentFile);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/video/init-stream1.m4s")
    @ResponseBody
    public ResponseEntity<Resource> serveVideoStream() {
        // Make sure the path is correct to where the .m4s files are located
        File videoSegment = new File("videos/init-stream1.m4s");
        if (!videoSegment.exists()) {
            return ResponseEntity.notFound().build(); // Return a 404 if the file doesn't exist
        }
        Resource resource = new FileSystemResource(videoSegment);
        return ResponseEntity.ok(resource);
    }
}

