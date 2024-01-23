package com.ssafy.api.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper {
  FileMapper instance = Mappers.getMapper(FileMapper.class);

}