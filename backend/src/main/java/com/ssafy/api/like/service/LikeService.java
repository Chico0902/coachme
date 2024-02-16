package com.ssafy.api.like.service;

import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.like.dto.response.CheckCoachLikeDto;
import com.ssafy.api.like.dto.response.CheckCoachingLikeDto;
import com.ssafy.api.like.repository.LikeRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Likes;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikeService {

  private final LikeRepository likeRepository;
  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;

  /**
   * 코치 찜콩
   */
  public void sendLikeCoach(Long coameId, Long coachId) throws Exception {
    Member coame = memberRepository.getReferenceById(coameId);
    Member coach = memberRepository.getReferenceById(coachId);
    if (coame.isCoachInLikes(coach)) {
      throw new Exception("like coach duplicated");
    }
    Likes like = new Likes();
    like.likeCoach(coame, coach);
  }

  /**
   * 코칭 찜콩
   */
  public void sendLikeCoaching(Long coameId, Long coachingId) throws Exception {
    Member coame = memberRepository.getReferenceById(coameId);
    Coaching coaching = coachingRepository.getReferenceById(coachingId);
    if (coame.isCoachingInLikes(coaching)) {
      throw new Exception("like coaching duplicated");
    }
    Likes like = new Likes();
    like.likeCoaching(coame, coaching);
  }

  /**
   * 코치 찜콩 취소
   */
  public void unlikeCoach(Long coameId, Long coachId) throws Exception {
    Member coame = memberRepository.getReferenceById(coameId);
    Member coach = memberRepository.getReferenceById(coachId);
    if (!coame.isCoachInLikes(coach)) {
      throw new Exception("like coach not found");
    }
    Likes like = likeRepository.findByCoameAndCoach(coame, coach);
    coame.getSendLikes().remove(like);
  }

  /**
   * 코칭 찜콩 취소
   */
  public void unlikeCoaching(Long coameId, Long coachingId) throws Exception {
    Member coame = memberRepository.getReferenceById(coameId);
    Coaching coaching = coachingRepository.getReferenceById(coachingId);
    if (coame.isCoachingInLikes(coaching)) {
      throw new Exception("like coaching not found");
    }
    Likes like = likeRepository.findByCoameAndCoaching(coame, coaching);
    coame.getSendLikes().remove(like);
  }

  /**
   * 코칭 찜콩 확인. 리턴값은 boolean
   */
  public CheckCoachingLikeDto checkLikeCoaching(Long coameId, Long coachingId) {
    Member coame = memberRepository.getReferenceById(coameId);
    Coaching coaching = coachingRepository.getReferenceById(coachingId);
    Likes like = likeRepository.findByCoameAndCoaching(coame, coaching);
    CheckCoachingLikeDto dto = new CheckCoachingLikeDto();
    if (like == null) {
      dto.setIslike(false);
    } else {
      dto.setIslike(true);
    }

    return dto;
  }

  /**
   * 코치 찜콩 확인. 리턴값은 boolean
   */
  public CheckCoachLikeDto checkLikeCoach(Long coameId, Long coachId) {
    Member coame = memberRepository.getReferenceById(coameId);
    Member coach = memberRepository.getReferenceById(coachId);
    Likes like = likeRepository.findByCoameAndCoach(coame, coach);
    CheckCoachLikeDto dto = new CheckCoachLikeDto();
    if (like == null) {
      dto.setIslike(false);
    } else {
      dto.setIslike(true);
    }

    return dto;
  }
}
