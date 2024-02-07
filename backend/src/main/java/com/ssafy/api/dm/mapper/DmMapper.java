package com.ssafy.api.dm.mapper;

import com.ssafy.api.dm.dto.DmRedisDto;
import com.ssafy.api.dm.dto.response.DmResponseDto;
import com.ssafy.api.dm.dto.response.DmRoomResponseDto;
import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DmMapper {
  DmMapper instance = Mappers.getMapper(DmMapper.class);

  @Mapping(source = "id", target = "roomId")
  @Mapping(source = "member1.name", target = "name")
  @Mapping(source = "member1.profileImage.url", target = "img")
  @Named("coach")
  DmRoomResponseDto dmRoomToDmRoomCoachResponseDto(DMRoom dmRoom);

  @Mapping(source = "id", target = "roomId")
  @Mapping(source = "member2.name", target = "name")
  @Mapping(source = "member2.profileImage.url", target = "img")
  @Named("coame")
  DmRoomResponseDto dmRoomToDmRoomCoameResponseDto(DMRoom dmRoom);

  @IterableMapping(qualifiedByName = {"coach"})
  List<DmRoomResponseDto> dmRoomToDmRoomCoachResponseDtoList(List<DMRoom> dmRoom);

  @IterableMapping(qualifiedByName = {"coame"})
  List<DmRoomResponseDto> dmRoomToDmRoomCoameResponseDtoList(List<DMRoom> dmRoom);
//  List<DmRoomResponseDto> dmRoomToDmRoomResponseDto(List<DMRoom> dmRoomList);


  @Mapping(source = "member.longId", target = "member")
  DmResponseDto DmToDmResponseDto(DM dm);

  List<DmResponseDto> DmToDmResponseDto(List<DM> dmList);

  DmResponseDto redisDtoToDmDto(DmRedisDto dmRedisDto);

  List<DmResponseDto> redisDtoToDmResponseDtoList(List<DmRedisDto> dmList);

}
