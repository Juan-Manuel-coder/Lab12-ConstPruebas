package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    @Mapping(source = "pet.id", target = "petId")
    VisitDTO toDTO(Visit visit);

    @Mapping(source = "petId", target = "pet.id")
    Visit toEntity(VisitDTO visitDTO);

    List<VisitDTO> toDTOList(List<Visit> visitList);
    List<Visit> toEntityList(List<VisitDTO> visitDTOList);
}