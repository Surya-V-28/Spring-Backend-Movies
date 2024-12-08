package com.moviefix.MovieApi.Video;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface VideoService {
     String uploadVideo(String name, MultipartFile file) throws IOException;

     InputStream getVideoStream(String path, String fileName) throws IOException;
}