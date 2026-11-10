package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner owner = ownerMapper.toEntity(ownerDTO);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.toDTO(savedOwner);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO, Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));
        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCity(ownerDTO.getCity());
        owner.setTelephone(ownerDTO.getTelephone());
        Owner updatedOwner = ownerRepository.save(owner);
        return ownerMapper.toDTO(updatedOwner);
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));
        ownerRepository.delete(owner);
    }

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        return owner.map(ownerMapper::toDTO)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));
    }

    @Override
    public List<OwnerDTO> findAll() {
        List<Owner> owners = ownerRepository.findAll();
        return ownerMapper.toDTOList(owners);
    }
}