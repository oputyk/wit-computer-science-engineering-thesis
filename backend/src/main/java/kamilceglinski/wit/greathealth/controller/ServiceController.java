package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.config.IsAdmin;
import kamilceglinski.wit.greathealth.dto.ServiceRequestDTO;
import kamilceglinski.wit.greathealth.dto.ServiceResponseDTO;
import kamilceglinski.wit.greathealth.service.ServiceService;
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
@RequestMapping("api/services")
public class ServiceController {

    private final ServiceService serviceService;

    @IsAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponseDTO createService(@RequestBody ServiceRequestDTO requestDTO) {
        return serviceService.createService(requestDTO);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponseDTO getServiceById(@PathVariable String uuid) {
        return serviceService.getServiceByUuId(uuid);
    }

    @IsAdmin
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteService(@PathVariable String uuid) {
        serviceService.deleteService(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceResponseDTO> getAllServices() {
        return serviceService.getAllServices();
    }
}
