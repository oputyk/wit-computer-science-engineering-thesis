package kamilceglinski.wit.greathealth.config;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean isAdmin(Authentication authentication) {
        return getGroups(authentication).contains("57057a39-a18f-4aed-bea8-c0f661ff12f0");
    }

    public boolean isDoctor(Authentication authentication) {
        return getGroups(authentication).contains("2350f29d-627c-4bd0-b554-e757825ba21c");
    }

    public boolean isPatient(Authentication authentication) {
        return getGroups(authentication).contains("973529ee-6146-4109-b7ef-21ea5f38efe2");
    }

    public List<String> getGroups(Authentication authentication) {
        return (List<String>) ((JwtAuthenticationToken) authentication).getToken().getClaims().getOrDefault("groups", List.of());
    }

    public String getEmail(Authentication authentication) {
        return (String) ((JwtAuthenticationToken) authentication).getToken().getClaims().get("preferred_username");
    }
}
