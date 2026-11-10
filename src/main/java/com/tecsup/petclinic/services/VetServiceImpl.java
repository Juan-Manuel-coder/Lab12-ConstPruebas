package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = vetMapper.toEntity(vetDTO);
        Vet savedVet = vetRepository.save(vet);
        return vetMapper.toDTO(savedVet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO, Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));
        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());
        Vet updatedVet = vetRepository.save(vet);
        return vetMapper.toDTO(updatedVet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));
        vetRepository.delete(vet);
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        return vet.map(vetMapper::toDTO)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));
    }

    @Override
    public List<VetDTO> findAll() {
        List<Vet> vets = vetRepository.findAll();
        return vetMapper.toDTOList(vets);
    }
}