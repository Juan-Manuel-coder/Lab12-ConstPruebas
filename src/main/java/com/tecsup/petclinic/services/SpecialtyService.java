package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

public interface SpecialtyService {
    SpecialtyDTO create(SpecialtyDTO specialtyDTO);
    SpecialtyDTO update(SpecialtyDTO specialtyDTO, Integer id) throws SpecialtyNotFoundException;
    void delete(Integer id) throws SpecialtyNotFoundException;
    SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;
    List<SpecialtyDTO> findAll();
}