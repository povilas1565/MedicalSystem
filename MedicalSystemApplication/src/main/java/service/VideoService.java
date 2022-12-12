package service;

import exceptions.VideoNotFoundException;
import model.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import repository.CallRepository;
import repository.UserRepository;
import repository.VideoRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class VideoService {

    public static final Logger LOG = LoggerFactory.getLogger(VideoService.class);
    private final VideoRepository videoRepository;
    private final CallRepository callRepository;
    private final UserRepository userRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository, CallRepository callRepository, UserRepository userRepository) {
        this.videoRepository = videoRepository;
        this.callRepository = callRepository;
        this.userRepository = userRepository;
    }

    public static byte[] compressVideo(byte[] data) {
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
            LOG.error("Cannot compress video");
        }
        System.out.println("Compressed video size = " + byteArrayOutputStream.toByteArray().length);
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] decompressVideo(byte[] data) {
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
            LOG.error(("cannot decompress video"));
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

    public Video getCallVideo(Long callId) {
        Video callVideo = videoRepository.findByCallId(callId)
                .orElseThrow(() -> new VideoNotFoundException("Video cannot found for call" + callId));
        if (!ObjectUtils.isEmpty(callVideo)) {
            callVideo.setVideoBytes(decompressVideo((callVideo.getVideoBytes())));
        }
        return callVideo;
    }


}
