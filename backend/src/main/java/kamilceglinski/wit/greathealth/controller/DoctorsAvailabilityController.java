package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.dto.DoctorsAvailabilityRequestDTO;
import kamilceglinski.wit.greathealth.dto.AvailabilityResponseDTO;
import kamilceglinski.wit.greathealth.service.DoctorsAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityResponseDTO createAvailability(@PathVariable String doctorUuid,
                                                      @RequestBody DoctorsAvailabilityRequestDTO requestDTO) {
        return doctorsAvailabilityService.createAvailability(doctorUuid, requestDTO);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public AvailabilityResponseDTO getAvailabilityById(@PathVariable String doctorUuid, @PathVariable String uuid) {
        return doctorsAvailabilityService.getAvailabilityByUuId(doctorUuid, uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAvailability(@PathVariable String doctorUuid, @PathVariable String uuid) {
        doctorsAvailabilityService.deleteAvailability(doctorUuid, uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvailabilityResponseDTO> getAllAvailabilities(@PathVariable String doctorUuid) {
        return doctorsAvailabilityService.getAllAvailabilities(doctorUuid);
    }
}
