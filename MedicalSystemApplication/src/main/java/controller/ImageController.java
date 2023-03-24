package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping(value = "api/image")
@CrossOrigin
@Api
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    @ApiOperation("Загрузка фоток для профилей пользователей")
    public ResponseEntity<MessageResponse> uploadImageToProfile(@RequestParam("file") MultipartFile file,
                                                                Principal principal) throws IOException {
        log.info("Uploading a photo for the user profile '{}'.", principal.getName());
        imageService.uploadImageToProfile(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image upload successfully"));
    }

    @GetMapping("/profileImage")
    @ApiOperation("Получение фоток профилей пользователей")
    public ResponseEntity<Image> getUserProfileImage(Principal principal) {
        log.info("Get photo for user profile '{}'.", principal.getName());
        Image profileImage = imageService.getUserProfileImage(principal);
        return new ResponseEntity<>(profileImage, HttpStatus.OK);
    }
}
