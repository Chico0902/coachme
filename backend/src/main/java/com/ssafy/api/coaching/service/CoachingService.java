package com.ssafy.api.coaching.service;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coaching.dto.request.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.response.CoachingDetailResponseDto;
import com.ssafy.api.coaching.dto.response.CoachingResponseDtos;
import com.ssafy.api.coaching.dto.response.CoameListResponseDto;
import com.ssafy.api.coaching.dto.response.CoachingDetailResponseDto;
import com.ssafy.api.coaching.mapper.CoachingMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.coaching.repository.LiveCoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.api.review.repository.ReviewRepository;
import com.ssafy.db.entity.*;
import com.ssafy.db.entity.type.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoachingService {

  private final LiveCoachingRepository liveCoachingRepository;
  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;
  private final CategoryRepository categoryRepository;
  private final ReviewRepository reviewRepository;

  /**
   * 라이브 코칭을 수강하는 코미 목록을 반환하는 메서드
   *
   * @param id - 라이브 코칭 id
   * @return - 코미 목록
   */
  @Transactional(readOnly = true)
  public List<CoameListResponseDto> getCoameList(Long id) {
    LiveCoaching liveCoaching = liveCoachingRepository.findByIdWithDetails(id);
    return CoachingMapper.instance.coachingToCoameListResponseDto(liveCoaching.getCoameCoachings());
  }

  /**
   * 라이브 코칭 신청 메서드
   *
   * @param liveCoachingId - 라이브 코칭 id
   * @param coameId        - 코미 id
   */
  public void signUpClass(Long liveCoachingId, Long coameId) {
    LiveCoaching liveCoaching = liveCoachingRepository.getReferenceById(liveCoachingId);
    Member member = memberRepository.getReferenceById(coameId);
    CoameCoaching coameCoaching = new CoameCoaching();
    coameCoaching.createCoaching(liveCoaching, member);
  }

  /**
   * 코칭 정보를 받아서 코칭 등록
   */
  public void createCoaching(Long longId, CreateCoachingRequestDto dto) {

    // 1. DB에서 멤버를 찾아온다
    Member memberInDB = memberRepository.getReferenceById(longId);
    Category main = categoryRepository.findByName(dto.getMain());
    Category sub = categoryRepository.findByName(dto.getSub());

    // 2. 새로운 코칭을 만든다.
    Coaching newCoaching = Coaching.builder()
        .name(dto.getName())
        .htmlDocs(dto.getHtmlDocs())
        .mainCategory(main)
        .subCategory(sub)
        .summary(dto.getSummary()).build();

    //   newCoaching.categorize(main, sub);
    if (sub == null) {
      newCoaching.addOneCategory(main);
      main.addOneCategory(newCoaching);
    } else {
      newCoaching.addBothCategories(main, sub);
      sub.addCategoryList(newCoaching);
    }

    // 3. 새롭게 만든 코칭에 맴버를 등록한다.
    newCoaching.registCoaching(memberInDB);
  }

  public List<CoachingResponseDtos> getCoachingList(CoachesRequestDto dto) {
    List<CoachingResponseDtos> list;
    Long mainCategoryId;

    if(dto.getDivision1().equals("all")){
      list = coachingRepository.findByCoachingCategory(null, null);
    }else if(dto.getDivision2().equals("all")){
      mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, dto.getDivision1());
      list = coachingRepository.findByCoachingCategory(mainCategoryId, null);
    }else {
      mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, dto.getDivision1());
      Long subCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.SUB, dto.getDivision2());

      list = coachingRepository.findByCoachingCategory(mainCategoryId, subCategoryId);
    }

    return list;
  }

  public CoachingDetailResponseDto getCoachingDetail(long coachingId) {
    CoachingDetailResponseDto dto =CoachingMapper.instance.coachingToCoachingDetailResponseDto(coachingRepository.getReferenceById(coachingId));

    List<Review> reviewList = reviewRepository.findAllByCoachingId(coachingId);
    long sum = 0;
    for (Review review : reviewList) {
      sum = review.getScore();
    }

    dto.setReviewCount(reviewList.size());
    if(sum != 0){
      dto.setReviewAvg((float) sum /reviewList.size());
    }else{
      dto.setReviewAvg(0);
    }

    return dto;
  }

}
