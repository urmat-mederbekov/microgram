package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.PostImageDTO;
import kz.attractorschool.microgram.service.PostImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class PostImageController {

    private final PostImageService postImageService;

    @PostMapping
    public PostImageDTO addPostImage(@RequestParam("file") MultipartFile file) {
        return postImageService.addImage(file);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> serveFile(@PathVariable String imageId) {
        Resource resource = postImageService.getById(imageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(resource);
    }
}
