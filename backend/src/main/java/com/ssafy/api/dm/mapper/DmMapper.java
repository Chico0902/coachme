package com.ssafy.api.dm.mapper;

import com.ssafy.api.dm.dto.response.DmListDto;
import com.ssafy.api.dm.dto.response.DmRoomResponseDto;
import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import org.mapstruct.IterableMapping;
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

  @Mapping(source = "id", target = "roomId")
  @Mapping(source = "member1.name", target = "memberName")
  @Mapping(source = "member1.profileImage.url", target = "memberProfileUrl")
  @Named("member1")
  DmRoomResponseDto dmRoomToDmRoomMember1ResponseDto(DMRoom dmRoom);

  @Mapping(source = "id", target = "roomId")
  @Mapping(source = "member2.name", target = "memberName")
  @Mapping(source = "member2.profileImage.url", target = "memberProfileUrl")
  @Named("member2")
  DmRoomResponseDto dmRoomToDmRoomMember2ResponseDto(DMRoom dmRoom);

  @IterableMapping(qualifiedByName = {"member2"})
  List<DmRoomResponseDto> dmRoomToDmRoomMember1ResponseDtoList(List<DMRoom> dmRoom);

  @IterableMapping(qualifiedByName = {"member1"})
  List<DmRoomResponseDto> dmRoomToDmRoomMember2ResponseDtoList(List<DMRoom> dmRoom);


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
//
//  DmResponseDto redisDtoToDmDto(DmRedisDto dmRedisDto);

//  List<DmResponseDto> redisDtoToDmResponseDtoList(List<DmRedisDto> dmList);

}
