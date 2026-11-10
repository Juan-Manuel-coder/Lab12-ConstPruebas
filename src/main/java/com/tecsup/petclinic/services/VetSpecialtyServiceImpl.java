package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mapper.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetSpecialtyMapper vetSpecialtyMapper;

    public VetSpecialtyServiceImpl(VetSpecialtyRepository vetSpecialtyRepository, VetSpecialtyMapper vetSpecialtyMapper) {
        this.vetSpecialtyRepository = vetSpecialtyRepository;
        this.vetSpecialtyMapper = vetSpecialtyMapper;
    }

    @Override
    public VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO) {
        VetSpecialty vetSpecialty = vetSpecialtyMapper.toEntity(vetSpecialtyDTO);
        VetSpecialty savedVetSpecialty = vetSpecialtyRepository.save(vetSpecialty);
        return vetSpecialtyMapper.toDTO(savedVetSpecialty);
    }

    @Override
    public VetSpecialtyDTO update(VetSpecialtyDTO vetSpecialtyDTO, Integer id) throws VetSpecialtyNotFoundException {
        VetSpecialty vetSpecialty = vetSpecialtyRepository.findById(id)
                .orElseThrow(() -> new VetSpecialtyNotFoundException("VetSpecialty not found with vetId: " + id));
        vetSpecialty.setSpecialtyId(vetSpecialtyDTO.getSpecialtyId());
        VetSpecialty saved = vetSpecialtyRepository.save(vetSpecialty);
        return vetSpecialtyMapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) throws VetSpecialtyNotFoundException {
        VetSpecialty vetSpecialty = vetSpecialtyRepository.findById(id)
                .orElseThrow(() -> new VetSpecialtyNotFoundException("VetSpecialty not found with vetId: " + id));
        vetSpecialtyRepository.delete(vetSpecialty);
    }

    @Override
    public VetSpecialtyDTO findById(Integer id) throws VetSpecialtyNotFoundException {
        Optional<VetSpecialty> vetSpecialty = vetSpecialtyRepository.findById(id);
        return vetSpecialty.map(vetSpecialtyMapper::toDTO)
                .orElseThrow(() -> new VetSpecialtyNotFoundException("VetSpecialty not found with vetId: " + id));
    }

    @Override
    public List<VetSpecialtyDTO> findAll() {
        List<VetSpecialty> list = vetSpecialtyRepository.findAll();
        return vetSpecialtyMapper.toDTOList(list);
    }
}