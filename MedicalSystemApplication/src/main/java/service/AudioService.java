package service;

import exceptions.AudioNotFoundException;
import model.Audio;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import repository.AudioRepository;
import repository.CallRepository;
import repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class AudioService {

    public static final Logger LOG = LoggerFactory.getLogger(AudioService.class);
    private final AudioRepository audioRepository;
    private final CallRepository callRepository;
    private final UserRepository userRepository;

    @Autowired
    public AudioService(AudioRepository audioRepository, CallRepository callRepository, UserRepository userRepository) {
        this.audioRepository = audioRepository;
        this.callRepository = callRepository;
        this.userRepository = userRepository;
    }

    public static byte[] compressAudio(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        byte[] segment = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(segment);
            byteArrayOutputStream.write(segment, 0, count);
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress audio");
        }
        System.out.println("Compressed audio size = " + byteArrayOutputStream.toByteArray().length);
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] decompressAudio(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        byte[] segment = new byte[1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(segment);
                byteArrayOutputStream.write(segment, 0, count);
            }
            byteArrayOutputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error(("cannot decompress audio"));
        }
        return byteArrayOutputStream.toByteArray();
    }

    public <T> Collector<T, ?, T> singleCallCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public Audio getCallAudio(Long callId) {
        Audio callAudio = audioRepository.findByCallId(callId)
                .orElseThrow(() -> new AudioNotFoundException("Audio cannot found for call" + callId));
        if (!ObjectUtils.isEmpty(callAudio)) {
            callAudio.setAudioBytes(decompressAudio((callAudio.getAudioBytes())));
        }
        return callAudio;
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }



}







