package kamilceglinski.wit.greathealth.controller;

import java.time.LocalDate;
import java.util.List;
import kamilceglinski.wit.greathealth.config.IsAdmin;
import kamilceglinski.wit.greathealth.config.IsAdminOrPatient;
import kamilceglinski.wit.greathealth.config.IsPatient;
import kamilceglinski.wit.greathealth.dto.AppointmentRequestDTO;
import kamilceglinski.wit.greathealth.dto.AppointmentResponseDTO;
import kamilceglinski.wit.greathealth.dto.AvailableAppointmentTimeResponseDTO;
import kamilceglinski.wit.greathealth.dto.PatientRequestDTO;
import kamilceglinski.wit.greathealth.dto.PatientResponseDTO;
import kamilceglinski.wit.greathealth.service.PatientService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/patients")
public class PatientController {

    private final PatientService patientService;
    private final UserService userService;

    @IsAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDTO createPatient(@RequestBody PatientRequestDTO requestDTO) {
        return patientService.createPatient(requestDTO);
    }

    @IsAdmin
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDTO updatePatient(@RequestBody PatientRequestDTO requestDTO, @PathVariable String uuid) {
        return patientService.updatePatient(requestDTO, uuid);
    }

    @IsAdmin
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String uuid) {
        patientService.deletePatient(uuid);
    }

    @IsAdminOrPatient
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public PatientResponseDTO getPatientById(@PathVariable String uuid,
                                             Authentication authentication) {
        if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return patientService.getPatientByUuId(uuid);
    }

    @IsAdmin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @IsPatient
    @PostMapping("/{uuid}/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDTO createAppointment(@PathVariable String uuid, @RequestBody AppointmentRequestDTO requestDTO,
                                                    Authentication authentication) {
        if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return patientService.createAppointment(uuid, requestDTO);
    }

    @IsPatient
    @GetMapping("/{uuid}/available-appointment-times")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableAppointmentTimeResponseDTO> getAvailableAppointmentTimes(@PathVariable String uuid,
                                                                                  @RequestParam String doctorUuid,
                                                                                  @RequestParam String serviceUuid,
                                                                                  @RequestParam LocalDate date,
                                                                                  Authentication authentication) {
        if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return patientService.getAvailableAppointmentTimes(uuid, doctorUuid, serviceUuid, date);
    }

    @IsPatient
    @GetMapping("/{uuid}/appointments")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponseDTO> getAppointments(@PathVariable String uuid,
                                                        Authentication authentication) {
        if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return patientService.getAppointments(uuid);
    }

    @IsPatient
    @DeleteMapping("/{uuid}/appointments/{appointmentUuid}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponseDTO cancelAppointment(@PathVariable String uuid, @PathVariable String appointmentUuid,
                                                    Authentication authentication) {
        if (!uuid.equals(userService.getCurrentUserUuid(authentication))) {
            throw new AuthorizationServiceException("Not authorized");
        }
        return patientService.cancelAppointment(uuid, appointmentUuid);
    }
}
