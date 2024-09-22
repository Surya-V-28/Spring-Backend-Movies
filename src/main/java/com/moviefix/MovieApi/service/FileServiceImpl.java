package com.moviefix.MovieApi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileServiceImpl  implements  FileService{
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
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
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
