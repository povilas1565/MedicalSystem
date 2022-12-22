package controller;

import model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import response.MessageResponse;
import service.ImageService;

import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping(value = "api/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToProfile(@RequestParam("file") MultipartFile file,
                                                                Principal principal) throws IOException {
        imageService.uploadImageToProfile(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image upload successfully"));
    }

    @GetMapping("/profileImage")
    public ResponseEntity<Image> getUserProfileImage(Principal principal) {
        Image profileImage = imageService.getUserProfileImage(principal);
        return new ResponseEntity<>(profileImage, HttpStatus.OK);

    }
}
