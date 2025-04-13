package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.config.IsAdminOrDoctor;
import kamilceglinski.wit.greathealth.config.IsDoctor;
import kamilceglinski.wit.greathealth.dto.DoctorsAvailabilityRequestDTO;
import kamilceglinski.wit.greathealth.dto.AvailabilityResponseDTO;
import kamilceglinski.wit.greathealth.service.DoctorsAvailabilityService;
import kamilceglinski.wit.greathealth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/doctors/{doctorUuid}/availabilities")
public class DoctorsAvailabilityController {

    private final DoctorsAvailabilityService doctorsAvailabilityService;
    private final UserService userService;

    @IsDoctor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityResponseDTO createAvailability(@PathVariable String doctorUuid,
                                                      @RequestBody DoctorsAvailabilityRequestDTO requestDTO,
                                                      Authentication authentication) {
        if (!doctorUuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return doctorsAvailabilityService.createAvailability(doctorUuid, requestDTO);
    }

    @IsDoctor
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public AvailabilityResponseDTO getAvailabilityById(@PathVariable String doctorUuid, @PathVariable String uuid,
                                                       Authentication authentication) {
        if (!doctorUuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return doctorsAvailabilityService.getAvailabilityByUuId(doctorUuid, uuid);
    }

    @IsDoctor
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvailabilityResponseDTO> getAllAvailabilities(@PathVariable String doctorUuid,
                                                              Authentication authentication) {
        if (!doctorUuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return doctorsAvailabilityService.getAllAvailabilities(doctorUuid);
    }
}
