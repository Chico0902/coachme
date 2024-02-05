package com.ssafy.api.dm.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DmRoomEnterResponseDto {

  long roomId;
  List<DmResponseDto> dmList;

}
