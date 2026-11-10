package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit visit = visitMapper.toEntity(visitDTO);
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO, Integer id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setDescription(visitDTO.getDescription());
        Visit updatedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(updatedVisit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
        visitRepository.delete(visit);
    }

    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {
        Optional<Visit> visit = visitRepository.findById(id);
        return visit.map(visitMapper::toDTO)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
    }

    @Override
    public List<VisitDTO> findAll() {
        List<Visit> visits = visitRepository.findAll();
        return visitMapper.toDTOList(visits);
    }
}