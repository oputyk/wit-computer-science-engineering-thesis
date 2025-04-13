package kamilceglinski.wit.greathealth.controller;

import java.util.List;
import kamilceglinski.wit.greathealth.config.IsAdmin;
import kamilceglinski.wit.greathealth.config.IsAdminOrDoctor;
import kamilceglinski.wit.greathealth.config.IsDoctor;
import kamilceglinski.wit.greathealth.config.SecurityService;
import kamilceglinski.wit.greathealth.dto.AppointmentResponseDTO;
import kamilceglinski.wit.greathealth.dto.DoctorRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorResponseDTO;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyResponseDTO;
import kamilceglinski.wit.greathealth.service.DoctorService;
import kamilceglinski.wit.greathealth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final SecurityService securityService;
    private final UserService userService;

    @IsAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO createDoctor(@RequestBody DoctorRequestDTO requestDTO) {
        return doctorService.createDoctor(requestDTO);
    }

    @IsAdmin
    @PostMapping("/{uuid}/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorSpecialtyResponseDTO createDoctorSpecialty(@RequestBody DoctorSpecialtyRequestDTO requestDTO,
                                                            @PathVariable String uuid) {
        return doctorService.createDoctorSpecialty(requestDTO, uuid);
    }

    @IsAdmin
    @DeleteMapping("/{uuid}/specialties/{specialtyUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteDoctorSpecialty(@PathVariable String uuid, @PathVariable String specialtyUuid) {
        doctorService.deleteDoctorSpecialty(uuid, specialtyUuid);
    }

    @IsAdmin
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO updateDoctor(@RequestBody DoctorRequestDTO requestDTO, @PathVariable String uuid) {
        return doctorService.updateDoctor(requestDTO, uuid);
    }

    @IsAdmin
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable String uuid) {
        doctorService.deleteDoctor(uuid);
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

    @IsAdminOrDoctor
    @GetMapping("/{uuid}/appointments")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponseDTO> getAppointments(@PathVariable String uuid,
                                                        Authentication authentication) {
        if (securityService.isDoctor(authentication)) {
            if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
                throw new AuthorizationServiceException("Not authorized");
            }
        }
        return doctorService.getAppointments(uuid);
    }

    @IsAdminOrDoctor
    @DeleteMapping("/{uuid}/appointments/{appointmentUuid}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponseDTO finishAppointment(@PathVariable String uuid, @PathVariable String appointmentUuid,
                                                    Authentication authentication) {
        if (securityService.isDoctor(authentication)) {
            if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
                throw new AuthorizationServiceException("Not authorized");
            }
        }
        return doctorService.finishAppointment(uuid, appointmentUuid);
    }
}
