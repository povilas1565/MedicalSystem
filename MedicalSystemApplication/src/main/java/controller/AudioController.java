package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AudioService;

@RestController
@RequestMapping(value = "api/audio")
@CrossOrigin
@Api

public class AudioController {

    @Autowired(required = false)
    private AudioService audioService;

    @GetMapping("/{callId}/audio")
    @ApiOperation("Получение аудио для звонка")
    public ResponseEntity<Audio> getCallAudio(@PathVariable String callId){
        Audio callAudio = audioService.getCallAudio(Long.parseLong(callId));
        return new ResponseEntity<>(callAudio, HttpStatus.OK);
    }
}

