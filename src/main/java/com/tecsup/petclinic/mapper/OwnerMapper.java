package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    OwnerDTO toDTO(Owner owner);
    Owner toEntity(OwnerDTO ownerDTO);
    List<OwnerDTO> toDTOList(List<Owner> ownerList);
    List<Owner> toEntityList(List<OwnerDTO> ownerDTOList);
}