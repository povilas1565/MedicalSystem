package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.VideoService;

@RestController
@RequestMapping(value = "api/video")
@CrossOrigin
@Api
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{callId}/video")
    @ApiOperation("Получение видео для звонка")
    public ResponseEntity<Video> getCallVideo(@PathVariable String callId){
        Video callVideo = videoService.getCallVideo(Long.parseLong(callId));
        return new ResponseEntity<>(callVideo, HttpStatus.OK);
    }
}
