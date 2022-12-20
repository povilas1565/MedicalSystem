package controller;

import model.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AudioService;

@RestController
@RequestMapping(value = "api/audio")
public class AudioController {

    @Autowired(required = false)
    private AudioService audioService;

    @GetMapping("/{callId}/audio")
    public ResponseEntity<Audio> getCallAudio(@PathVariable String callId){
        Audio callAudio = audioService.getCallAudio(Long.parseLong(callId));
        return new ResponseEntity<>(callAudio, HttpStatus.OK);
    }
}

