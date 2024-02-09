package com.ssafy.api.coach.service;

import com.ssafy.api.coach.dto.request.CreateLiveRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.*;
import com.ssafy.api.coach.mapper.CoachMapper;
import com.ssafy.api.coaching.dto.response.CoachDetail;
import com.ssafy.api.coaching.mapper.CoachingMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.coaching.repository.LiveCoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.api.review.repository.ReviewRepository;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.File;
import com.ssafy.db.entity.LiveCoaching;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.type.CategoryType;
import com.ssafy.util.file.Mapper.FileMapper;
import com.ssafy.util.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final FileRepository fileRepository;
  private final LiveCoachingRepository liveCoachingRepository;

  public PortfolioResponseDto getPortfolio(long id) {
    return CoachMapper.instance.PortfolioToPortfolioResponseDto(memberRepository.getReferenceById(id).getPortfolio());
  }

  public void updatePortfolio(long id, PortfolioRequestDto dto) {
    memberRepository.getReferenceById(id).updatePortfolio(dto.getHtmlDocs());
  }

  /**
   * 분류별 코치 정보 조회
   */
  public List<CoachesResponseDtos> getCoachList(String division1, String division2, String words) {
    List<CoachesResponseDtos> list;
    Long mainCategoryId;

    if (words.equals("all")) {
      words = null;
    }

    if (division1.equals("all")) {
      list = coachingRepository.findByCoachCategory(null, null, words);
    } else if (division2.equals("all")) {
      mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, division1);
      list = coachingRepository.findByCoachCategory(mainCategoryId, null, words);
    } else {
      mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, division1);
      Long subCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.SUB, division2);

      list = coachingRepository.findByCoachCategory(mainCategoryId, subCategoryId, words);
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

    List<LiveCoaching> liveCoachingList = liveCoachingRepository.findByCoachId(longId);

    return CoachingMapper.instance.liveCoachingToCalendarResponseDto(liveCoachingList);
  }

  /**
   * 코치의 영상 목록을 리스트로 가져오는 메서드
   *
   * @param coachId - 코치 pk
   * @return - 파일 목록 리스트
   */
  public List<VideoResponseDto> getVideos(Long coachId) {

    List<File> fileList = fileRepository.findByCoachIdWithCoaching(coachId);

    return FileMapper.instance.fileToVideoResponseDto(fileList);
  }

  /**
   * 코치가 라이브 코칭을 생성하는 메서드
   *
   * @param createLiveRequestDto - 생성할 라이브 코칭 정보
   */
  public void createLiveCoaching(CreateLiveRequestDto createLiveRequestDto) {

    Coaching coaching = coachingRepository.getReferenceById(createLiveRequestDto.getCoachingId());
    LiveCoaching liveCoaching = new LiveCoaching();
    liveCoaching.createLiveCoaching(coaching, createLiveRequestDto.getDate());
    liveCoachingRepository.save(liveCoaching);
  }
}
