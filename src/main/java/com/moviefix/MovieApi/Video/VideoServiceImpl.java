package com.moviefix.MovieApi.Video;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class VideoServiceImpl implements VideoService{
    @Override
    public String uploadVideo(String path, MultipartFile file) throws IOException {
        // to get the file name
        String fileName = file.getOriginalFilename();

        // to create the path for the file
        String filePath = path + File.separator + fileName;

        // create the file objects
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        // to copy the file form the user to our sever created paths
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getVideoStream(String path, String fileName) throws FileNotFoundException,IOException {
                String filePath = path + File.separator + fileName;
        File videoFile = new File(filePath);
        if (!videoFile.exists()) {
            throw new FileNotFoundException("Video file not found: " + fileName);
        }
            return new FileInputStream(filePath);
        }

    }
