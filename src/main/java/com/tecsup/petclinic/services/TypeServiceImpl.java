package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeDTO create(TypeDTO typeDTO) {
        Type type = typeMapper.toEntity(typeDTO);
        Type savedType = typeRepository.save(type);
        return typeMapper.toDTO(savedType);
    }

    @Override
    public TypeDTO update(TypeDTO typeDTO, Integer id) throws TypeNotFoundException {
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException("Type not found with id: " + id));
        type.setName(typeDTO.getName());
        Type updatedType = typeRepository.save(type);
        return typeMapper.toDTO(updatedType);
    }

    @Override
    public void delete(Integer id) throws TypeNotFoundException {
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException("Type not found with id: " + id));
        typeRepository.delete(type);
    }

    @Override
    public TypeDTO findById(Integer id) throws TypeNotFoundException {
        Optional<Type> type = typeRepository.findById(id);
        return type.map(typeMapper::toDTO)
                .orElseThrow(() -> new TypeNotFoundException("Type not found with id: " + id));
    }

    @Override
    public List<TypeDTO> findAll() {
        List<Type> types = typeRepository.findAll();
        return typeMapper.toDTOList(types);
    }
}