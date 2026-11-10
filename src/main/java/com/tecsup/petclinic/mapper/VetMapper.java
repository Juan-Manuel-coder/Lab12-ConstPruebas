package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VetMapper {
    VetMapper INSTANCE = Mappers.getMapper(VetMapper.class);

    VetDTO toDTO(Vet vet);
    Vet toEntity(VetDTO vetDTO);
    List<VetDTO> toDTOList(List<Vet> vetList);
    List<Vet> toEntityList(List<VetDTO> vetDTOList);
}