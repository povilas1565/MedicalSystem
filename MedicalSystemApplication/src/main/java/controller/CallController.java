package controller;

import dto.CallDTO;
import facade.CallFacade;
import model.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import response.MessageResponse;
import service.CallService;
import validators.ResponseErrorValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/api/call")
@CrossOrigin
public class CallController {

    @Autowired(required = false)
    private CallFacade callFacade;

    @Autowired
    private CallService callService;

    @Autowired(required = false)
    private ResponseErrorValidator responseErrorValidator;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody CallDTO callDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return listErrors;

        Call call = callService.createCall(callDTO, principal);
        CallDTO callCreated = callFacade.callToCallDTO(call);

        return new ResponseEntity<>(callCreated, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CallDTO>> getAllCalls() {
        List<CallDTO> callDTOList = callService.getAllCalls()
                .stream()
                .map(callFacade::callToCallDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(callDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/calls")
    public ResponseEntity<List<CallDTO>> getAllCallsForUser(Principal principal) {
        List<CallDTO> postDTOList = callService.getAllCallsForUser(principal)
                .stream()
                .map(callFacade::callToCallDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @PostMapping("/{callId}/delete")
    public ResponseEntity<MessageResponse> deleteCall(@PathVariable("callId") String callId, Principal principal) {
        callService.deleteCall(Long.parseLong(callId), principal);
        return new ResponseEntity<>(new MessageResponse("The call" + callId + "were deleted"), HttpStatus.OK);
    }
}
