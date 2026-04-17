package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.healthFacility.HealthFacilityRequestDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityResponseDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityUpdateDTO;
import com.hackathon.estoque.exception.healthFacility.HealthFacilityAlreadyExistsException;
import com.hackathon.estoque.exception.healthFacility.HealthFacilityNotFoundException;
import com.hackathon.estoque.exception.shared.InvalidRequiredAttributeException;
import com.hackathon.estoque.mapper.healthFacility.HealthFacilityMapper;
import com.hackathon.estoque.model.Address;
import com.hackathon.estoque.model.health.HealthFacility;
import com.hackathon.estoque.repository.AddressRepository;
import com.hackathon.estoque.repository.HealthFacilityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HealthFacilityService {
    private final HealthFacilityRepository healthFacilityRepository;
    private final HealthFacilityMapper healthFacilityMapper;
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    public HealthFacilityResponseDTO createHealthFacility(@Valid HealthFacilityRequestDTO healthFacilityRequestDTO)
            throws HealthFacilityAlreadyExistsException {
        if (healthFacilityRepository.existsByCnes(healthFacilityRequestDTO.getCnes())) {
            throw new HealthFacilityAlreadyExistsException("Estabelecimento de saúde já está cadastrado.");
        }
        Address address = addressService.createAddressHealthFacility(healthFacilityRequestDTO.getAddress());
        HealthFacility healthFacility = healthFacilityMapper.toEntity(healthFacilityRequestDTO);
        healthFacility.setAddress(address);
        HealthFacility savedHealthFacility = healthFacilityRepository.save(healthFacility);

        address.setHealthFacility(savedHealthFacility);
        addressRepository.save(address);
        return healthFacilityMapper.toResponseDTO(savedHealthFacility);
    }

    public HealthFacility updateEntityFromDTO(@Valid HealthFacilityUpdateDTO healthFacilityUpdateDTO) throws InvalidRequiredAttributeException {
        // Check CNES
        if (healthFacilityUpdateDTO.getCnes() == null || healthFacilityUpdateDTO.getCnes().isEmpty()) {
            throw new InvalidRequiredAttributeException("CNES não pode ser vazio ou nulo.");
        }
        HealthFacility healthFacility = healthFacilityRepository.findByCnes(healthFacilityUpdateDTO.getCnes())
                .orElseThrow(() -> new HealthFacilityNotFoundException("Estabelecimento de saúde não encontrado com CNES: " + healthFacilityUpdateDTO.getCnes()));
        // Update data -> Don't update ID or CNES
        healthFacility.setName(healthFacilityUpdateDTO.getName());
        healthFacility.setEmail(healthFacilityUpdateDTO.getEmail());
        healthFacility.setPhone(healthFacilityUpdateDTO.getPhone());
        healthFacility.setActive(true);
        // Update existed Address related to HealthFacility
        Address foundAddress = addressRepository.findByHealthFacilityId(healthFacility.getId())
                .orElse(null);

        if (foundAddress != null) {
            foundAddress.setStreet(healthFacilityUpdateDTO.getAddress().getStreet());
            foundAddress.setNumber(healthFacilityUpdateDTO.getAddress().getNumber());
            foundAddress.setComplement(healthFacilityUpdateDTO.getAddress().getComplement());
            foundAddress.setCity(healthFacilityUpdateDTO.getAddress().getCity());
            foundAddress.setCep(healthFacilityUpdateDTO.getAddress().getCep());
            foundAddress.setState(healthFacilityUpdateDTO.getAddress().getState());

            addressRepository.save(foundAddress);
            healthFacility.setAddress(foundAddress);
        }
        return healthFacilityRepository.save(healthFacility);
    }

    public Boolean deleteHealthFacility(String cnes) {
        HealthFacility healthFacility = healthFacilityRepository.findByCnes(cnes)
                .orElseThrow(() -> new HealthFacilityNotFoundException("Estabelecimento de saúde não encontrado com CNES: " + cnes));
        healthFacility.setActive(false);
        healthFacilityRepository.save(healthFacility);
        return true;
    }

    public HealthFacility getHealthFacilityByCnes(String cnes) throws InvalidRequiredAttributeException {
        if (cnes == null || cnes.isBlank()) {
            throw new InvalidRequiredAttributeException("CNES não pode ser nulo.");
        }
        Optional<HealthFacility> healthFacility = healthFacilityRepository.findByCnes(cnes);
        if(healthFacility.isPresent()){
            HealthFacility foundHealthFacility = healthFacility.get();
            Address address = getAddressByHealthFacilityId(foundHealthFacility.getId());
            if(address!=null) foundHealthFacility.setAddress(address);
            return foundHealthFacility;
        }
        return null;
    }

    private Address getAddressByHealthFacilityId(Long id) {
        return addressRepository.findByHealthFacilityId(id)
                .orElse(null);
    }

    public List<HealthFacilityResponseDTO> getAllHealthFacilities() {
        List<HealthFacility> healthFacilities = healthFacilityRepository.findAll();
        if (healthFacilities.isEmpty()) {
            throw new HealthFacilityNotFoundException("Nenhum estabelecimento de saúde encontrado.");
        }
        return healthFacilityMapper.toResponseDTOList(healthFacilities);
    }

    public List<HealthFacility> getHealthFacilitiesByName(String name) throws InvalidRequiredAttributeException {
        if (name == null || name.isEmpty()) {
            throw new InvalidRequiredAttributeException("Nome não pode ser vazio.");
        }
        List<HealthFacility> healthFacilities = healthFacilityRepository.findByNameContainingIgnoreCase(name);
        if (healthFacilities.isEmpty()) {
            throw new HealthFacilityNotFoundException("Estabelecimento de saúde não encontrado com nome: " + name);
        }
        return healthFacilities;
    }
    
    public List<HealthFacility> getHealthFacilitiesByCep(String cep) throws InvalidRequiredAttributeException {
        if (cep == null || cep.isEmpty()) {
            throw new InvalidRequiredAttributeException("CEP não pode ser vazio.");
        }
        List<HealthFacility> healthFacilities = healthFacilityRepository.findByCep(cep);
        if (healthFacilities.isEmpty()) {
            throw new HealthFacilityNotFoundException("Estabelecimento de saúde não encontrado com CEP: " + cep);
        }
        return healthFacilities;
    }


}
