package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VetSpecialtyMapper {
    VetSpecialtyMapper INSTANCE = Mappers.getMapper(VetSpecialtyMapper.class);

    VetSpecialtyDTO toDTO(VetSpecialty vetSpecialty);
    VetSpecialty toEntity(VetSpecialtyDTO vetSpecialtyDTO);
    List<VetSpecialtyDTO> toDTOList(List<VetSpecialty> vetSpecialtyList);
    List<VetSpecialty> toEntityList(List<VetSpecialtyDTO> vetSpecialtyDTOList);
}