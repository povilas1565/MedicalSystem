package controller;

import model.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AudioService;

@RestController
@RequestMapping(value = "api/audio")
@CrossOrigin

public class AudioController {

    @Autowired(required = false)
    private AudioService audioService;

    @GetMapping("/{callId}/audio")
    public ResponseEntity<Audio> getCallAudio(@PathVariable String callId){
        Audio callAudio = audioService.getCallAudio(Long.parseLong(callId));
        return new ResponseEntity<>(callAudio, HttpStatus.OK);
    }
}

