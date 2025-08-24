package com.example.homeplanner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ReceiptStorageService {

    private final Path root;

    public ReceiptStorageService(@Value("${receipts.dir:uploads}") String dir) throws IOException {
        this.root = Paths.get(dir);
        Files.createDirectories(root);
    }

    public String store(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path dest = root.resolve(filename);
        Files.copy(file.getInputStream(), dest);
        return dest.toString();
    }
}

