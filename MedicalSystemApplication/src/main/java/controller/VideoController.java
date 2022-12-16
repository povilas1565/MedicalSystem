package controller;

import model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.VideoService;

@RestController
@RequestMapping(value = "api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{callId}/video")
    public ResponseEntity<Video> getCallVideo(@PathVariable String callId){
        Video callVideo = videoService.getCallVideo(Long.parseLong(callId));
        return new ResponseEntity<>(callVideo, HttpStatus.OK);
    }
}
