package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.PostImageDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.PostImage;
import kz.attractorschool.microgram.repository.PostImageRepo;
import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PostImageService {
    public final PostImageRepo postImageRepo;

    public PostImageService(PostImageRepo postImageRepo) {
        this.postImageRepo = postImageRepo;
    }
    public PostImageDTO addImage(MultipartFile file) {
        byte[] data = new byte[0];
        try {
            data = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data.length == 0) {
            // TODO return no content or something or throw exception
            //  which will be processed on controller layer
        }

        Binary bData = new Binary(data);
        PostImage image = PostImage.builder().postData(bData).build();

        postImageRepo.save(image);

        return PostImageDTO.builder()
                .imageId(image.getId())
                .build();
    }

    public Resource getById(String imageId) {
        PostImage postImage = postImageRepo.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Image with " + imageId + " doesn't exists!"));
        return new ByteArrayResource(postImage.getPostData().getData());
    }
}
