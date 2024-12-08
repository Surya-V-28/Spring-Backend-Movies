package com.moviefix.MovieApi.Video;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class VideoUploadController {

    private final VideoServiceImpl videoService;
    private final ResourceLoader resourceLoader;

    public VideoUploadController(VideoServiceImpl videoService, ResourceLoader resourceLoader) {
        this.videoService = videoService;
        this.resourceLoader = resourceLoader;
    }

    @Value("${project.videos}")
    private String path;

    @PostMapping
    public ResponseEntity<String> uploadVideo(@RequestPart MultipartFile file) {
        try {
            String pathName = videoService.uploadVideo(path, file);
            return ResponseEntity.ok("Uploaded the video successfully: " + pathName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while uploading video: " + e.getMessage());
        }
    }
    @GetMapping("/video/{pathid}")
    public ResponseEntity<Resource> getVideo(@PathVariable String pathid) {
        try {
            // Prefix the path with "file:" to indicate a file resource
            Resource resource = resourceLoader.getResource("file:" + path + "/" + pathid);
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "video/mp4");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e + " error for the Video Controller in Java Spring boot");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
