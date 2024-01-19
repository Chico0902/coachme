package com.ssafy.api.member.mapper;
import com.ssafy.api.member.dto.UploadProfileRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface FileMapper {
  FileMapper instance = Mappers.getMapper(FileMapper.class);

}