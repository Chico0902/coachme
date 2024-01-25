package com.ssafy.api.dm.mapper;

import com.ssafy.api.dm.dto.DmDto;
import com.ssafy.api.dm.dto.DmRedisDto;
import com.ssafy.api.dm.dto.DmRoomRequestDto;
import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DmMapper {
  DmMapper instance = Mappers.getMapper(DmMapper.class);

  @Mapping(source = "dmroomId", target = "roomId")
  @Mapping(source = "coame.id", target = "coameId")
  @Mapping(source = "coach.id", target = "coachId")
  DmRoomRequestDto dmRoomTodmRoomRequestDto(DMRoom dmRoom);
  List<DmRoomRequestDto> dmRoomTodmRoomListRequestDto(List<DMRoom> dmRoomList);

  @Mapping(source = "member.id", target = "member")
  DmDto DmToDmDto(DM dm);
  List<DmDto> DmToDmDtoList(List<DM> dmList);

  DmDto redisDtoToDmDto(DmRedisDto dmRedisDto);
  List<DmDto> redisDtoToDmDtoList(List<DmRedisDto> dmList);

}
