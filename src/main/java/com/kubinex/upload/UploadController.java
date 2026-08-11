package com.kubinex.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/upload")
public class UploadController {

    private final Path uploadDir;

    public UploadController(@Value("${app.upload.dir}") String dir) throws IOException {
        this.uploadDir = Path.of(dir);
        Files.createDirectories(this.uploadDir);
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), uploadDir.resolve(filename));
        return ResponseEntity.ok("/uploads/" + filename);
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> serve(@PathVariable String filename) throws MalformedURLException {
        Path file = uploadDir.resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (!resource.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }
}
