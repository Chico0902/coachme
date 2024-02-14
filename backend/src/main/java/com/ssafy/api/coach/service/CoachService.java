package com.ssafy.api.coach.service;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coach.dto.request.CreateLiveRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.*;
import com.ssafy.api.coach.mapper.CoachMapper;
import com.ssafy.api.coaching.dto.response.CoachDetail;
import com.ssafy.api.coaching.mapper.CoachingMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.coaching.repository.LiveCoachingRepository;
import com.ssafy.api.member.dto.response.ProfileImageResponseDto;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.api.review.repository.ReviewRepository;
import com.ssafy.db.entity.*;
import com.ssafy.util.file.Mapper.FileMapper;
import com.ssafy.util.file.repository.FileRepository;
import com.ssafy.util.file.service.FileService;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoachService {

  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;
  private final ReviewRepository reviewRepository;
  private final FileRepository fileRepository;
  private final LiveCoachingRepository liveCoachingRepository;
  private final FileService fileService;




  public PortfolioResponseDto getPortfolio(long id) {
    return CoachMapper.instance.PortfolioToPortfolioResponseDto(memberRepository.getReferenceById(id).getPortfolio());
  }

  public void updatePortfolio(long id, PortfolioRequestDto dto) {
    memberRepository.getReferenceById(id).updatePortfolio(dto.getHtmlDocs());
  }

  /**
   * 분류별 코치 정보 조회
   */
  public List<CoachesResponseDto> getCoachList(String division1, String division2, CoachesRequestDto coachesRequestDto) {
    List<CoachesResponseDto> coachingList = new ArrayList<>();
    Set<Long> idSet = new HashSet<>();

    List<Coaching> coachings = coachingRepository.findByCoachCategory(division1, division2, coachesRequestDto.getWords(), coachesRequestDto.getLoginMemberId());
    for (Coaching coaching : coachings) {
      if (idSet.contains(coaching.getCoach().getLongId())) continue;

      CoachesResponseDto dto = new CoachesResponseDto();
      dto.setCoachId(coaching.getCoach().getLongId());
      dto.setMemberName(coaching.getCoach().getName());
      dto.setProfileImg(coaching.getCoach().getProfileImage().getUrl());
      dto.setLessonInfo(coaching.getSummary());

      if (!coaching.getCoach().getReceivedReviews().isEmpty()) {
        int sum = 0;
        for (Review review : coaching.getCoach().getReceivedReviews()) {
          sum += review.getScore();
        }
        if (sum != 0) {
          dto.setAvgScore((float) (sum / coaching.getCoach().getReceivedReviews().size()));
        }
        dto.setReviewCount(coaching.getCoach().getReceivedReviews().size());
      } else {
        dto.setReviewCount(0);
        dto.setAvgScore(0.0F);
      }
      coachingList.add(dto);
      idSet.add(coaching.getCoach().getLongId());
    }

    return coachingList;
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

  /**
   * 메인페이지_인기 코치 조회
   */
  public List<PopularCoachResponseDto> getPopularCoach() {
    List<PopularCoachResponseDto> popularList = new ArrayList<>();

    List<Member> coachList = memberRepository.findByPopularCoach();
    
    for (Member coach : coachList) {
      PopularCoachResponseDto dto = new PopularCoachResponseDto();
      dto.setCoachId(coach.getLongId());
      dto.setCoachName(coach.getName());
      dto.setCoachProfileImageUrl(coach.getProfileImage().getUrl());
      if (!coach.getCoachTeachCourses().isEmpty()) {
        dto.setCoachingInfo(coach.getCoachTeachCourses().get(0).getName());
      } else {
        dto.setCoachingInfo("");
      }

      int sum = 0;
      for (Review review : coach.getReceivedReviews()) {
        sum += review.getScore();
      }
      if (!coach.getReceivedReviews().isEmpty()) {
        dto.setCoachingReviewAvg((float) sum / coach.getReceivedReviews().size());
      } else {
        dto.setCoachingReviewAvg(0);
      }
      popularList.add(dto);
    }

    return popularList;
  }

  public void uploadVideo(Long coachId, MultipartFile videoFile, String fileName, long coachingId) {
    String url = fileService.uploadFileList(Arrays.asList(videoFile));
    Member member = memberRepository.getReferenceById(coachId);
    Coaching coaching = coachingRepository.getReferenceById(coachingId);

    File file = File.builder()
        .uploader(member)
        .url(url)
        .name(fileName)
        .coaching(coaching)
        .build();
    
    coaching.addVideoCoaching(file);
    fileRepository.save(file);
  }
}
