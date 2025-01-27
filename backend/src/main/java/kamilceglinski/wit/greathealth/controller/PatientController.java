package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.dto.PatientRequestDTO;
import kamilceglinski.wit.greathealth.dto.PatientResponseDTO;
import kamilceglinski.wit.greathealth.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDTO createPatient(@RequestBody PatientRequestDTO requestDTO) {
        return patientService.createPatient(requestDTO);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDTO updatePatient(@RequestBody PatientRequestDTO requestDTO, @PathVariable String uuid) {
        return patientService.updatePatient(requestDTO, uuid);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public PatientResponseDTO getPatientById(@PathVariable String uuid) {
        return patientService.getPatientByUuId(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }
}
