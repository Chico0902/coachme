package com.ssafy.util.file.Mapper;

import com.ssafy.api.coach.dto.response.VideoResponseDto;
import com.ssafy.api.coaching.dto.response.VideosInCoachingResponseDto;
import com.ssafy.db.entity.File;
import com.ssafy.util.file.dto.response.FileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FileMapper {

  FileMapper instance = Mappers.getMapper(FileMapper.class);

  @Mapping(target = "fileName", source = "name")
  @Mapping(target = "fileUrl", source = "url")
  FileResponseDto fileToFileResponseDto(File file);

  List<FileResponseDto> fileToFileResponseDtoList(List<File> fileList);

  @Mapping(source = "id", target = "videoId")
  @Mapping(source = "name", target = "videoName")
  @Mapping(source = "coaching.id", target = "coachingId")
  @Mapping(source = "coaching.name", target = "coachingName")
  VideoResponseDto fileToVideoResponseDto(File file);

  List<VideoResponseDto> fileToVideoResponseDto(List<File> file);

  VideosInCoachingResponseDto fileToVideosInCoachingResponseDto(File file);

  List<VideosInCoachingResponseDto> fileToVideosInCoachingResponseDto(List<File> file);

}
