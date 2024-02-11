package com.ssafy.api.dm.service;

import com.ssafy.api.dm.dto.request.DmSocketRequestMessage;
import com.ssafy.api.dm.dto.response.DmSocketResponseMessage;
import com.ssafy.api.dm.util.RedisUtils;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DmSocketService {
  private final MemberRepository memberRepository;
  private final RedisUtils redisUtils;

  public DmSocketResponseMessage sendDm(DmSocketRequestMessage message, String roomId) {
    LocalDateTime now = LocalDateTime.now();
    String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String key = roomId + "_" + message.getSender() + "_" + time;
    String value = message.getContent();
    redisUtils.save(key, value);
    DmSocketResponseMessage returnMessage = new DmSocketResponseMessage();

    long id = Long.parseLong(message.getSender());
    Member member = memberRepository.getReferenceById(id);

    returnMessage.setMemberId(id);
    returnMessage.setMemberName(member.getName());
    returnMessage.setMemberProfileUrl(member.getProfileImage().getUrl());
    returnMessage.setCreateDate(now.truncatedTo(ChronoUnit.MINUTES).toString());
    returnMessage.setMessage(Collections.singletonList(value));

    return returnMessage;
  }
}
