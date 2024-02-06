package com.ssafy.api.coach.service;

import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.CalendarResponseDto;
import com.ssafy.api.coach.dto.response.CoachDetailResponseDto;
import com.ssafy.api.coach.dto.response.CoachesResponseDtos;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.mapper.CoachMapper;
import com.ssafy.api.coaching.dto.response.CoachDetail;
import com.ssafy.api.coaching.mapper.CoachingMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.api.review.repository.ReviewRepository;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.LiveCoaching;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.type.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoachService {
  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;
  private final CategoryRepository categoryRepository;
  private final ReviewRepository reviewRepository;

  public PortfolioResponseDto getPortfolio(long id) {
    return CoachMapper.instance.PortfolioToPortfolioResponseDto(memberRepository.getReferenceById(id).getPortfolio());
  }

  public void updatePortfolio(long id, PortfolioRequestDto dto) {
    memberRepository.getReferenceById(id).updatePortfolio(dto.getHtmlDocs());
  }

  /**
   * 분류별 코치 정보 조회
   */
  public List<CoachesResponseDtos> getCoachList(String division1, String division2) {
    List<CoachesResponseDtos> list;
    Long mainCategoryId;

    if (division1.equals("all")) {
      list = coachingRepository.findByCoachCategory(null, null);
    } else if (division2.equals("all")) {
      mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, division1);
      list = coachingRepository.findByCoachCategory(mainCategoryId, null);
    } else {
      mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, division1);
      Long subCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.SUB, division2);

      list = coachingRepository.findByCoachCategory(mainCategoryId, subCategoryId);
    }

    return list;
  }

  /**
   * 코치 상세 페이지 조회
   */
  public CoachDetailResponseDto getCoachDetail(long coachId) {
    CoachDetailResponseDto dto = CoachMapper.instance.memberToCoachDetailResponseDto(memberRepository.getReferenceById(coachId));
    List<CoachDetail> coaching = CoachingMapper.instance.coachingToCoachDetailList(coachingRepository.findByCoachId(coachId));
    dto.setList(coaching);

    List<Review> reviewList = reviewRepository.findAllByCoachId(coachId);
    long sum = 0;
    for (Review review : reviewList) {
      sum += review.getScore();
    }

    dto.setReviewCount(reviewList.size());
    if (sum != 0) {
      dto.setReviewAvg((float) sum / reviewList.size());
    } else {
      dto.setReviewAvg(0);
    }

    return dto;
  }

  /**
   * 코치가 생성한 모든 라이브 코칭 정보를 리스트로 반환하는 메서드
   *
   * @param longId - 코치 ID
   * @return - 라이브 코칭 정보 리스트
   */
  public List<CalendarResponseDto> getCalender(Long longId) {

    List<Coaching> coachingList = coachingRepository.findByLiveCoachingCoachId(longId);
    List<CalendarResponseDto> list = new ArrayList<>();

    for (Coaching c : coachingList) {
      for (LiveCoaching lc : c.getLiveCoachings()) {

        CalendarResponseDto calendarResponseDto = new CalendarResponseDto();
        calendarResponseDto.setId(lc.getId());
        calendarResponseDto.setClassName(c.getName());

        String[] dateAndTime = getDateAndTime(lc.getCoachingDate());
        calendarResponseDto.setDate(dateAndTime[0]);
        calendarResponseDto.setTime(dateAndTime[1]);
        list.add(calendarResponseDto);
      }
    }

    return list;
  }

  /**
   * 시간을 날짜와 시간으로 포맷팅하는 메서드
   *
   * @param localDateTime - 라이브 코칭 시간
   * @return - 날짜와 시간을 담은 String 배열
   */
  public String[] getDateAndTime(LocalDateTime localDateTime) {

    String[] result = new String[2];
    DateTimeFormatter format_date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    DateTimeFormatter format_time = DateTimeFormatter.ofPattern("HH:mm");

    result[0] = localDateTime.format(format_date);
    result[1] = localDateTime.format(format_time);

    return result;
  }
}
