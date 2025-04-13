package kamilceglinski.wit.greathealth.controller;

import kamilceglinski.wit.greathealth.config.IsDoctorOrPatient;
import kamilceglinski.wit.greathealth.dto.CurrentUserUuidResponseDTO;
import kamilceglinski.wit.greathealth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @IsDoctorOrPatient
    @GetMapping("current/uuid")
    @ResponseStatus(HttpStatus.OK)
    public CurrentUserUuidResponseDTO getCurrentUserUuid(Authentication authentication) {
        CurrentUserUuidResponseDTO currentUserUuidResponseDTO = new CurrentUserUuidResponseDTO();
        currentUserUuidResponseDTO.setUuid(userService.getCurrentUserUuid(authentication));
        return currentUserUuidResponseDTO;
    }
}
