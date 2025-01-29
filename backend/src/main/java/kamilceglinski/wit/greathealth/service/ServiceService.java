package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.data.repository.ServiceRepository;
import kamilceglinski.wit.greathealth.data.repository.SpecialtyRepository;
import kamilceglinski.wit.greathealth.dto.ServiceRequestDTO;
import kamilceglinski.wit.greathealth.dto.ServiceResponseDTO;
import kamilceglinski.wit.greathealth.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class ServiceService {

    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;
    private final SpecialtyRepository specialtyRepository;

    public ServiceResponseDTO createService(ServiceRequestDTO requestDTO) {
        SpecialtyEntity specialtyEntity = specialtyRepository.findById(requestDTO.getSpecialtyUuid()).orElseThrow();
        ServiceEntity serviceEntity = serviceMapper.toServiceEntity(requestDTO, specialtyEntity);
        ServiceEntity savedServiceEntity = serviceRepository.save(serviceEntity);
        return serviceMapper.toServiceResponseDTO(savedServiceEntity);
    }

    public ServiceResponseDTO getServiceByUuId(String uuid) {
        return serviceRepository.findById(uuid)
            .map(serviceMapper::toServiceResponseDTO)
            .orElseThrow();
    }

    public List<ServiceResponseDTO> getAllServices() {
        return serviceRepository.findAll().stream()
            .map(serviceMapper::toServiceResponseDTO)
            .collect(Collectors.toList());
    }
}
