package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.dto.SpecialtyNameResponseDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyResponseDTO;
import kamilceglinski.wit.greathealth.service.SpecialtyService;
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
@RequestMapping("specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialtyResponseDTO createSpecialty(@RequestBody SpecialtyRequestDTO requestDTO) {
        return specialtyService.createSpecialty(requestDTO);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public SpecialtyResponseDTO getSpecialtyById(@PathVariable String uuid) {
        return specialtyService.getSpecialtyByUuId(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SpecialtyNameResponseDTO> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }
}
