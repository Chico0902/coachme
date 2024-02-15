package com.ssafy.api.coaching.service;

import com.ssafy.api.coach.dto.response.CoachesCoachingsResponseDto;
import com.ssafy.api.coaching.dto.request.CoachingInfoChangeRequestDto;
import com.ssafy.api.coaching.dto.request.CoachingRequestDto;
import com.ssafy.api.coaching.dto.request.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.request.VideoRequestDto;
import com.ssafy.api.coaching.dto.response.*;
import com.ssafy.api.coaching.mapper.CoachingMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.coaching.repository.LiveCoachingRepository;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.api.review.repository.ReviewRepository;
import com.ssafy.db.entity.*;
import com.ssafy.util.file.Mapper.FileMapper;
import com.ssafy.util.file.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
  private final FileRepository fileRepository;

  /**
   * 라이브 코칭을 수강하는 코미 목록을 반환하는 메서드
   *
   * @param id - 라이브 코칭 id
   * @return - 코미 목록
   */
  @Transactional(readOnly = true)
  public List<CoameListResponseDto> getCoameList(Long id) {
    List<Member> memberList = memberRepository.findByLiveCoachingIdWithDetails(id);
    return MemberMapper.instance.memberToCoameListResponseDto(memberList);
  }

  /**
   * 라이브 코칭 신청 메서드
   *
   * @param liveCoachingId - 라이브 코칭 id
   * @param coameId        - 코미 id
   */
  public void signUpClass(Long liveCoachingId, Long coameId) throws Exception {
    LiveCoaching liveCoaching = liveCoachingRepository.getReferenceById(liveCoachingId);
    Member member = memberRepository.getReferenceById(coameId);
    if (member.getCoameTaughtCourses() != null) {
      for (CoameCoaching coameCoaching : member.getCoameTaughtCourses()) {
        if (coameCoaching.getLiveCoaching() == liveCoaching) {
          throw new Exception("sign up duplicated");
        }
      }
      CoameCoaching newCoameCoaching = new CoameCoaching();
      newCoameCoaching.createCoaching(liveCoaching, member);
    }

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


  /**
   * 분류별 코칭 정보 조회
   */
  public List<CoachingResponseDto> getCoachingList(String division1, String division2, CoachingRequestDto coachingRequestDto) {
    List<CoachingResponseDto> coachingList = new ArrayList<>();

    List<Coaching> coachings = coachingRepository.findByCoachingCategory(division1, division2, coachingRequestDto.getWords(), coachingRequestDto.getLoginMemberId());

    for (Coaching coaching : coachings) {
      CoachingResponseDto dto = new CoachingResponseDto();
      dto.setCoachId(coaching.getCoach().getLongId());
      dto.setMemberName(coaching.getCoach().getName());
      dto.setProfileImg(coaching.getCoach().getProfileImage().getUrl());
      dto.setCoachingId(coaching.getId());
      dto.setCoachingName(coaching.getName());

      if (!coaching.getReceivedReviews().isEmpty()) {
        int sum = 0;
        for (Review review : coaching.getReceivedReviews()) {
          sum += review.getScore();
        }
        if (sum != 0) {
          dto.setAvgScore((float) (sum / coaching.getReceivedReviews().size()));
        }
        dto.setReviewCount(coaching.getReceivedReviews().size());
      } else {
        dto.setReviewCount(0);
        dto.setAvgScore(0.0F);
      }
      coachingList.add(dto);
    }

    return coachingList;
  }

  /**
   * 코칭 상세 페이지 조회
   */
  public CoachingDetailResponseDto getCoachingDetail(long coachingId) {
    CoachingDetailResponseDto dto = CoachingMapper.instance.coachingToCoachingDetailResponseDto(coachingRepository.getReferenceById(coachingId));

    List<Review> reviewList = reviewRepository.findAllByCoachingId(coachingId);
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
   * 해당 코치의 코칭 1개 조회
   */
  @Transactional(readOnly = true)
  public GetOneCoachingResponseDto getOneCoaching(Long longId, Long id) {
    Member member = memberRepository.getReferenceById(longId);
    List<Coaching> coachings = member.getCoachTeachCourses();
    log.info(String.valueOf(coachings.size()));
//    Coaching foundCoaching;
    for (Coaching coaching : coachings) {
      if (coaching.getId() == id) {
        return CoachingMapper.instance.coachingToGetOneCoachingResponseDto(coaching);
      }
    }
    throw new EntityNotFoundException();
  }

  /**
   * 해당 코치의 모든 코칭 조회
   */
  @Transactional(readOnly = true)
  public List<CoachesCoachingsResponseDto> getCoachesCoachings(Long longId) {
    Member member = memberRepository.getReferenceById(longId);
    List<Coaching> coachings = member.getCoachTeachCourses();
    List<CoachesCoachingsResponseDto> list = CoachingMapper.instance.coachingToCoachesCoachingsResponseDto(coachings);

    return list;
  }

  /**
   * 해당 코치의 코칭 정보 수정
   */
  public void changeCoachingInfo(Long longId, Long id, CoachingInfoChangeRequestDto dto) {
    Member member = memberRepository.getReferenceById(longId);
    List<Coaching> coachings = member.getCoachTeachCourses();
    for (Coaching coaching : coachings) {
      if (coaching.getId() == id) {
        Category main = categoryRepository.findByName(dto.getMain());
        Category sub = categoryRepository.findByName(dto.getSub());
        coaching.updatedCoaching(main, sub, dto.getName(), dto.getSummary(), dto.getHtmlDocs());
      }
    }
  }

  /**
   * 해당 코치의 코칭 삭제
   */
  public void deleteCoaching(Long longId, Long id) {
    Coaching coaching = coachingRepository.getReferenceById(id);
    coachingRepository.delete(coaching);
  }

  /**
   * 메인페이지_인기 코칭 조회
   */
  public List<CoachingPopularResponseDto> getPopularCoaching() {
    List<CoachingPopularResponseDto> popularList = new ArrayList<>();

    List<Coaching> coachingList = coachingRepository.findByPopularCoaching();

    for (Coaching list : coachingList) {
      CoachingPopularResponseDto dto = new CoachingPopularResponseDto();
      dto.setCoachingId(list.getId());
      dto.setCoachingName(list.getName());

      int sum = 0;
      for (Review review : list.getReceivedReviews()) {
        sum += review.getScore();
      }
      if (!list.getReceivedReviews().isEmpty()) {
        dto.setCoachingReviewAvg((float) sum / list.getReceivedReviews().size());
      }
      if (list.getRepresent() != null) {
        dto.setCoachingVideoUrl(list.getRepresent().getUrl());
      }
      popularList.add(dto);
    }

    return popularList;
  }

  /**
   * 코칭 pk와 파일 pk를 받아 코칭의 대표 동영상으로 등록시켜주는 메서드
   */
  public void registRepresentVideo(Long coachingId, Long fileId) {
    Coaching coaching = coachingRepository.getReferenceById(coachingId);
    File file = fileRepository.getReferenceById(fileId);
    coaching.registRepresent(file);
  }

  /**
   * 코칭의 라이브코칭 리스트를 가져오는 메서드
   */
  public List<LiveCoachingsResponseDto> getLiveCoachingsByCoachingId(Long coachingId) {
    List<LiveCoaching> liveCoachingList = liveCoachingRepository.findByCoachingId(coachingId);
    return CoachingMapper.instance.liveCoachingToResponseDto(liveCoachingList);
  }

  /**
   * 코칭의 영상 리스트를 가져오는 메서드
   */
  public List<VideosInCoachingResponseDto> getVideos(Long coachingId) {
    List<File> videosList = fileRepository.findByCoachingId(coachingId);
    return FileMapper.instance.fileToVideosInCoachingResponseDto(videosList);
  }

  public void registEditedVideo(Long coachingId, VideoRequestDto videoRequestDto) {
    Coaching coaching = coachingRepository.getReferenceById(coachingId);
    Member member = memberRepository.getReferenceById(videoRequestDto.getCoachId());
    File newfile = new File();
    newfile.createFile(member, coaching, videoRequestDto.getUrl(), videoRequestDto.getVideoName());
    fileRepository.save(newfile);
  }
}
