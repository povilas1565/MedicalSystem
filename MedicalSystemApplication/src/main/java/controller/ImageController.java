package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.ImageService;
import java.io.IOException;

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
    public ResponseEntity<String> uploadImageToProfile(@RequestParam("id") Long id,
                                                       @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Upload a photo for the user profile with id '{}'.", id);
        imageService.uploadImageToProfile(id, file);
        return ResponseEntity.ok("Image upload successfully");
    }

    @GetMapping("/profileImage")
    @ApiOperation("Получение фоток профилей пользователей")
    public ResponseEntity<byte[]> getUserProfileImage(@RequestParam("id") Long id) {
        log.info("Get photo for user profile with id '{}'.", id);
        return imageService.getUserProfileImage(id);
    }
}
