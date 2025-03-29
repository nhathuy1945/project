package com.babycenter.pregnancy_tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {
    public String storeFile(MultipartFile file) throws IOException {
        // Implement file storage logic
        return "file_path";
    }
}