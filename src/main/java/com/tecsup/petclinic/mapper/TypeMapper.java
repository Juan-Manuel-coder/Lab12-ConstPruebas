package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    TypeDTO toDTO(Type type);
    Type toEntity(TypeDTO typeDTO);
    List<TypeDTO> toDTOList(List<Type> typeList);
    List<Type> toEntityList(List<TypeDTO> typeDTOList);
}