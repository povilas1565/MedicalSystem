package controller;

import model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.VideoService;

@RestController
@RequestMapping(value = "api/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{callId}/video")
    public ResponseEntity<Video> getCallVideo(@PathVariable String callId){
        Video callVideo = videoService.getCallVideo(Long.parseLong(callId));
        return new ResponseEntity<>(callVideo, HttpStatus.OK);
    }
}
