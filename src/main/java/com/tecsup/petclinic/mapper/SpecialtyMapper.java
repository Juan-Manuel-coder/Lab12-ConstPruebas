package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {
    SpecialtyMapper INSTANCE = Mappers.getMapper(SpecialtyMapper.class);

    SpecialtyDTO toDTO(Specialty specialty);
    Specialty toEntity(SpecialtyDTO specialtyDTO);
    List<SpecialtyDTO> toDTOList(List<Specialty> specialtyList);
    List<Specialty> toEntityList(List<SpecialtyDTO> specialtyDTOList);
}