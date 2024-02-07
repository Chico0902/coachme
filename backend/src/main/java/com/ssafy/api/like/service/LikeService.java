package com.ssafy.api.like.service;

import com.ssafy.api.like.repository.LikeRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Likes;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeService {
  private final MemberRepository memberRepository;
  private final LikeRepository likeRepository;

  public void likeCoach(Long coachId, Long coameId) {
    Member coame = memberRepository.getReferenceById(coameId);
    Member coach = memberRepository.getReferenceById(coachId);
    Likes like = Likes.builder()
        .coame(coame)
        .coach(coach)
        .
  }
}
