package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {
    VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO);
    VetSpecialtyDTO update(VetSpecialtyDTO vetSpecialtyDTO, Integer id) throws VetSpecialtyNotFoundException;
    void delete(Integer id) throws VetSpecialtyNotFoundException;
    VetSpecialtyDTO findById(Integer id) throws VetSpecialtyNotFoundException;
    List<VetSpecialtyDTO> findAll();
}