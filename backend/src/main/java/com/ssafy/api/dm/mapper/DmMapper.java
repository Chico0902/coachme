package com.ssafy.api.dm.mapper;

import com.ssafy.api.dm.dto.response.DmListDto;
import com.ssafy.db.entity.DM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper
public interface DmMapper {
  DmMapper instance = Mappers.getMapper(DmMapper.class);

  @Mapping(source = "member.longId", target = "memberId")
  @Mapping(source = "member.name", target = "memberName")
  @Mapping(source = "member.profileImage.url", target = "memberProfileUrl")
  @Mapping(source = "createDate", target = "createDate", qualifiedByName = "truncateDateTimeToMinute")
  DmListDto DmToDmResponseDto(DM dm);

  @Named("truncateDateTimeToMinute")
  default String truncateDateTimeToMinute(LocalDateTime dateTime) {
    return dateTime.truncatedTo(ChronoUnit.MINUTES).toString();
  }

  List<DmListDto> DmToDmResponseDto(List<DM> dmList);

}
