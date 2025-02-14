package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.dto.DoctorRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorResponseDTO;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyResponseDTO;
import kamilceglinski.wit.greathealth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO createDoctor(@RequestBody DoctorRequestDTO requestDTO) {
        return doctorService.createDoctor(requestDTO);
    }

    @PostMapping("/{uuid}/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorSpecialtyResponseDTO createDoctorSpecialty(@RequestBody DoctorSpecialtyRequestDTO requestDTO,
                                                            @PathVariable String uuid) {
        return doctorService.createDoctorSpecialty(requestDTO, uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO updateDoctor(@RequestBody DoctorRequestDTO requestDTO, @PathVariable String uuid) {
        return doctorService.updateDoctor(requestDTO, uuid);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorResponseDTO getDoctorById(@PathVariable String uuid) {
        return doctorService.getDoctorByUuId(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DoctorResponseDTO> getAllDoctors(@RequestParam(name = "specialtyUuid", required = false) String specialtyUuid) {
        return doctorService.getAllDoctors(specialtyUuid);
    }
}
