package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.dto.AvailabilityRequestDTO;
import kamilceglinski.wit.greathealth.dto.AvailabilityResponseDTO;
import kamilceglinski.wit.greathealth.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("{doctorUuid}/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityResponseDTO createAvailability(@PathVariable String doctorUuid,
                                                      @RequestBody AvailabilityRequestDTO requestDTO) {
        return availabilityService.createAvailability(doctorUuid, requestDTO);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public AvailabilityResponseDTO getAvailabilityById(@PathVariable String doctorUuid, @PathVariable String uuid) {
        return availabilityService.getAvailabilityByUuId(doctorUuid, uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvailabilityResponseDTO> getAllAvailabilities(@PathVariable String doctorUuid) {
        return availabilityService.getAllAvailabilities(doctorUuid);
    }
}
