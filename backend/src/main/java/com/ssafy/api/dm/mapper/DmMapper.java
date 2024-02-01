package com.ssafy.api.dm.mapper;

import com.ssafy.api.dm.dto.DmRedisDto;
import com.ssafy.api.dm.dto.response.DmResponseDto;
import com.ssafy.api.dm.dto.response.DmRoomResponseDto;
import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DmMapper {
  DmMapper instance = Mappers.getMapper(DmMapper.class);

  @Mapping(source = "id", target = "roomId")
  @Mapping(source = "coame.longId", target = "coameId")
  @Mapping(source = "coach.longId", target = "coachId")
  DmRoomResponseDto dmRoomTodmRoomRequestDto(DMRoom dmRoom);
  List<DmRoomResponseDto> dmRoomTodmRoomRequestDtoList(List<DMRoom> dmRoomList);


  @Mapping(source = "member.longId", target = "member")
  DmResponseDto DmToDmResponseDto(DM dm);
  List<DmResponseDto> DmToDmResponseDto(List<DM> dmList);

  DmResponseDto redisDtoToDmDto(DmRedisDto dmRedisDto);
  List<DmResponseDto> redisDtoToDmResponseDtoList(List<DmRedisDto> dmList);

}
