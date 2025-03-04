package com.example.MicroService_Streamer.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:5500")
@Controller
public class HTMLController {

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        // You can add any attributes to the model here if needed
        return "upload"; // This will render upload.html
    }
}
