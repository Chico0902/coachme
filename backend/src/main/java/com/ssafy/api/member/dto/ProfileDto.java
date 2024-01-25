package com.ssafy.api.member.dto;

import com.ssafy.db.entity.member.Member;
import lombok.Data;

@Data
public class ProfileDto {
  //  String memberId;
  Member member;
  String name;
  String url;
  String type;
  String format;
}
