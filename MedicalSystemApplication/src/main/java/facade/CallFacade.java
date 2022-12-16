package facade;

import dto.CallDTO;
import model.Call;
import org.springframework.stereotype.Component;

@Component
public class CallFacade {
    public CallDTO callToCallDTO(Call call) {
        CallDTO callDTO = new CallDTO();
        callDTO.setId(call.getId());
        callDTO.setStartTime(call.getStartTime());
        callDTO.setEndTime(call.getEndTime());
        callDTO.setDuration(call.getDuration());
        callDTO.setAudio(call.getAudio());
        callDTO.setVideo(call.getVideo());

        return callDTO;
    }
}


