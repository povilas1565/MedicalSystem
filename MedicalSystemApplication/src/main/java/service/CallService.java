package service;

import dto.CallDTO;
import exceptions.CallNotFoundException;
import model.Audio;
import model.Call;
import model.User;
import model.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.AudioRepository;
import repository.CallRepository;
import repository.UserRepository;
import repository.VideoRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {
    public static final Logger LOG = LoggerFactory.getLogger(CallService.class);

    private final CallRepository callRepository;
    private final UserRepository userRepository;
    private final AudioRepository audioRepository;
    private final VideoRepository videoRepository;

    @Autowired

    public CallService(CallRepository callRepository, UserRepository userRepository, AudioRepository audioRepository,VideoRepository videoRepository) {
        this.callRepository = callRepository;
        this.userRepository = userRepository;
        this.audioRepository = audioRepository;
        this.videoRepository = videoRepository;
    }

    public List<Call> getAllCalls() {
        return callRepository.findAll();
    }

    public Call getCallById(Long callId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return callRepository.findCallByIdAndUsers(callId, user)
                .orElseThrow(() -> new CallNotFoundException("Call not found for username:" + user.getEmail()));
    }


    public List<Call> getAllCallsForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return callRepository.findAllByUsers(user);
    }

    public Call createCall(CallDTO callDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Call call = new Call();
        call.setUsers((List<User>)user);
        call.setStartTime(callDTO.getStartTime());
        call.setEndTime(callDTO.getEndTime());
        call.setDuration(call.getDuration());

        LOG.info("Create new call for user: {}", user.getEmail());
        return callRepository.save(call);
    }

    public void deleteCall(Long callId, Principal principal) {
        Call call = getCallById(callId, principal);
        Optional<Audio> audio = audioRepository.findByCallId(call.getId());
        Optional<Video> video = videoRepository.findByCallId(call.getId());
        callRepository.delete(call);
        audio.ifPresent(audioRepository::delete);
        video.ifPresent(videoRepository::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }

}

