package kamilceglinski.wit.greathealth.service;

import kamilceglinski.wit.greathealth.config.SecurityService;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import kamilceglinski.wit.greathealth.data.repository.DoctorRepository;
import kamilceglinski.wit.greathealth.data.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final SecurityService securityService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public String getCurrentUserUuid(Authentication authentication) {
        String email = securityService.getEmail(authentication);
        if (securityService.isDoctor(authentication)) {
            return doctorRepository.findByEmail(email)
                .map(DoctorEntity::getUuid)
                .orElse(null);
        } else if (securityService.isPatient(authentication)) {
            return patientRepository.findByEmail(email)
                .map(PatientEntity::getUuid)
                .orElse(null);
        }
        return null;
    }
}
